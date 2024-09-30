package com.moyunzhijiao.system_app.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moyunzhijiao.system_app.controller.dto.fonted.WordInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.WordListInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.analysis.ExerciseAnalysisInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.analysis.ExerciseWordRecordInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.analysis.WordAnalysisInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.analysis.WordAnalysisListInfo;
import com.moyunzhijiao.system_app.entity.Radical;
import com.moyunzhijiao.system_app.entity.Structure;
import com.moyunzhijiao.system_app.entity.exercise.CharacterAnalysis;
import com.moyunzhijiao.system_app.entity.exercise.Homework;
import com.moyunzhijiao.system_app.entity.exercise.HomeworkSubmission;
import com.moyunzhijiao.system_app.entity.exercise.StrokeAnalysis;
import com.moyunzhijiao.system_app.entity.word.TemplateWord;
import com.moyunzhijiao.system_app.mapper.RadicalMapper;
import com.moyunzhijiao.system_app.mapper.StructureMapper;
import com.moyunzhijiao.system_app.mapper.exercise.*;
import com.moyunzhijiao.system_app.mapper.word.TemplateWordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalysisService {
    @Autowired
    HomeworkSubmissionMapper homeworkSubmissionMapper;
    @Autowired
    HomeworkMapper homeworkMapper;

    // 获取练习数据分析
    public List<ExerciseAnalysisInfo> getExerciseAnalysis(Integer userId, String year, String month) {
        // 检查并去掉年份和月份末尾的 "年" 和 "月"
        if (!year.equals("全部年份")) {
            year = year.replace("年", "");
        }
        if (!month.equals("全部月份")) {
            month = month.replace("月", "");
        }

        // 查询用户的所有练习记录，且 state 为 1
        QueryWrapper<HomeworkSubmission> submissionQueryWrapper = new QueryWrapper<>();
        submissionQueryWrapper.eq("student_id", userId)
                .eq("state", 1);
        List<HomeworkSubmission> submissions = homeworkSubmissionMapper.selectList(submissionQueryWrapper);

        // 定义日期格式
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 创建返回的练习分析信息列表
        List<ExerciseAnalysisInfo> analysisInfoList = new ArrayList<>();
        for (HomeworkSubmission submission : submissions) {
            // 格式化时间为仅包含日期部分
            LocalDate submissionDate = LocalDateTime.parse(submission.getSubmitedTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    .toLocalDate();
            String formattedDate = submissionDate.format(dateFormatter);

            // 过滤符合条件的记录
            boolean matchesYear = year.equals("全部年份") || submissionDate.getYear() == Integer.parseInt(year);
            boolean matchesMonth = month.equals("全部月份") || submissionDate.getMonthValue() == Integer.parseInt(month);

            if (matchesYear && matchesMonth) {
                Homework homework = homeworkMapper.selectById(submission.getHomeworkId());
                boolean isSelfExercise = homework.getIsSelf() == 1;

                analysisInfoList.add(new ExerciseAnalysisInfo(
                        submission.getHomeworkId(), // 返回 homework 的 ID
                        isSelfExercise, // 返回练习类型
                        homework.getName(),
                        formattedDate,
                        submission.getSystemScore() != null ? submission.getSystemScore() : 0
                ));
            }
        }

        return analysisInfoList;
    }



//                    homeworkMapper.selectById(submission.getHomeworkId()).getName(),

    @Autowired
    CharacterAnalysisMapper characterAnalysisMapper;
    @Autowired
    TemplateWordMapper templateWordMapper;
    @Autowired
    RadicalMapper radicalMapper;
    @Autowired
    StructureMapper structureMapper;

    //获取所有字分析列表
    public List<WordAnalysisListInfo> getWordAnalysisList(Integer userId, String search, String radical, String structure, String typeface, Integer pageNumber, Integer pageSize) {
        System.out.println("getWordAnalysisList" + structure + radical + pageNumber);

        // 从 character_analysis 表中统计数据
        Map<String, WordStats> wordStatsMap = new HashMap<>();
        List<CharacterAnalysis> characterAnalyses = characterAnalysisMapper.selectByUserId(userId);
        for (CharacterAnalysis analysis : characterAnalyses) {
            WordStats stats = wordStatsMap.getOrDefault(analysis.getName(), new WordStats());
            stats.addScore(analysis.getScore());
            stats.incrementExerciseCount();
            wordStatsMap.put(analysis.getName(), stats);
        }

        // 从 template_word 表中筛选数据
        QueryWrapper<TemplateWord> queryWrapper = new QueryWrapper<>();
        if (search != null && !search.isEmpty()) {
            queryWrapper.like("name", search);
        }
        List<TemplateWord> templateWords = templateWordMapper.selectList(queryWrapper);

        // 结合其他表的数据进行筛选
        List<WordListInfo> wordListInfos = templateWords.stream()
                .map(templateWord -> {
                    Radical radicalEntity = radicalMapper.selectById(templateWord.getRadicalId());
                    Structure structureEntity = structureMapper.selectById(templateWord.getStructureId());
                    String radicalName = radicalEntity != null ? radicalEntity.getName() : null;
                    String structureName = structureEntity != null ? structureEntity.getName() : null;

                    if ((radical == null || radical.isEmpty() || radical.equals(radicalName)) &&
                            (structure == null || structure.isEmpty() || structure.equals(structureName))) {
                        return new WordListInfo(templateWord.getId(), templateWord.getContent(), templateWord.getName());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // 分页处理
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, wordListInfos.size());
        List<WordListInfo> paginatedWordListInfos = wordListInfos.subList(start, end);

        // 合并结果
        List<WordAnalysisListInfo> wordAnalysisList = new ArrayList<>();
        for (WordListInfo wordListInfo : paginatedWordListInfos) {
            WordStats stats = wordStatsMap.getOrDefault(wordListInfo.getName(), new WordStats());
            WordAnalysisListInfo info = new WordAnalysisListInfo(wordListInfo.getId(), stats.getAverageScore(), stats.getExerciseCount(), wordListInfo.getTemplate());
            wordAnalysisList.add(info);
        }

        return wordAnalysisList;
    }


    // 辅助类用于存储字的统计信息
    class WordStats {
        private int totalScore = 0;
        private int exerciseCount = 0;

        public void addScore(int score) {
            this.totalScore += score;
        }

        public void incrementExerciseCount() {
            this.exerciseCount++;
        }

        public double getAverageScore() {
            return exerciseCount == 0 ? 0 : (double) totalScore / exerciseCount;
        }

        public int getExerciseCount() {
            return exerciseCount;
        }
    }

    @Autowired
    StrokeAnalysisMapper strokeAnalysisMapper;

    // 获取单字分析
    public WordAnalysisInfo getSingleWordAnalysis(Integer userId, Integer wordId) {
        // 查询模板字信息
        TemplateWord templateWord = templateWordMapper.selectById(wordId);
        if (templateWord == null) {
            throw new IllegalArgumentException("Invalid wordId: " + wordId);
        }
        String wordName = templateWord.getName();
        String template = templateWord.getContent();

        // 查询字符分析数据
        List<CharacterAnalysis> characterAnalyses = characterAnalysisMapper.selectByUserIdAndWordName(userId, wordName);

        // 按创建时间排序，并只保留最新的七次记录
        characterAnalyses.sort(Comparator.comparing(CharacterAnalysis::getCreatedTime).reversed());
        List<CharacterAnalysis> latestAnalyses = characterAnalyses.stream().limit(7).collect(Collectors.toList());
        System.out.println("latestAnalyses: " + latestAnalyses.size() + latestAnalyses);

        int[] exerciseNumber = new int[latestAnalyses.size()];
        double[] score = new double[latestAnalyses.size()];
        double totalScore = 0;

        // 储存最新的七次练习分数
        int i = 0;
        for (CharacterAnalysis analysis : latestAnalyses) {
            score[i] = analysis.getScore();
            i++;
        }

        // 获取练习记录
        List<ExerciseWordRecordInfo> recordList = new ArrayList<>();
        Map<Integer, List<ExerciseWordRecordInfo>> groupedRecords = new HashMap<>();

        for (CharacterAnalysis analysis : characterAnalyses) {
            // 计算总得分
            totalScore += analysis.getScore();

            // 查询作业提交信息
            HomeworkSubmission submission = homeworkSubmissionMapper.selectById(analysis.getSubmissionId());
            if (submission != null) {
                // 查询笔画信息
                List<StrokeAnalysis> strokes = strokeAnalysisMapper.selectByCharacterAnalysisId(analysis.getId());
                List<String> strokePictures = strokes.stream().map(StrokeAnalysis::getPicture).collect(Collectors.toList());

                ExerciseWordRecordInfo record = new ExerciseWordRecordInfo(
                        submission.getId(),
                        homeworkMapper.selectById(submission.getHomeworkId()).getName(), // 这里可以根据实际情况设置标题
                        submission.getSubmitedTime(),
                        submission.getSystemScore() != null ? submission.getSystemScore() : submission.getTeacherScore(),
                        new WordInfo(
                                analysis.getPicture(),
                                strokePictures, // 设置笔画信息
                                analysis.getScore(),
                                template,
                                analysis.getEvaluation()
                        )
                );
                recordList.add(record);

                // 按作业ID归类记录
                groupedRecords.computeIfAbsent(submission.getHomeworkId(), k -> new ArrayList<>()).add(record);
            }
        }

        // 打印所有得分
        System.out.println("Score ALL: " + Arrays.toString(score));

        // 计算平均得分
        double averageScore = characterAnalyses.size() > 0 ? totalScore / characterAnalyses.size() : 0;

        // 获取评价建议（这里可以根据实际情况生成）
        String suggest = "继续努力，保持进步！"; // 这里应该有对应的算法得到分析结果的评语

        // 创建并返回 WordAnalysisInfo 对象
        WordAnalysisInfo wordAnalysisInfo = new WordAnalysisInfo(
                wordId,
                averageScore,
                exerciseNumber,
                suggest,
                template,
                score,
                recordList.toArray(new ExerciseWordRecordInfo[0])
        );

        return wordAnalysisInfo;
    }


}

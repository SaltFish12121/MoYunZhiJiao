package com.moyunzhijiao.system_app.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.moyunzhijiao.system_app.controller.dto.CreateDTO;
import com.moyunzhijiao.system_app.controller.dto.Generate;
import com.moyunzhijiao.system_app.controller.dto.UploadDTO;
import com.moyunzhijiao.system_app.controller.dto.fonted.*;
import com.moyunzhijiao.system_app.controller.dto.fonted.competition.CompetitionContentInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.excellent.ExcellentCompetitionInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.excellent.ExcellentHomeworkInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.excellent.ExcellentWorkInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.exercise.*;
import com.moyunzhijiao.system_app.entity.*;
import com.moyunzhijiao.system_app.entity.collection.ExerciseCollection;
import com.moyunzhijiao.system_app.entity.competition.*;
import com.moyunzhijiao.system_app.entity.exercise.*;
import com.moyunzhijiao.system_app.entity.word.TemplateWord;
import com.moyunzhijiao.system_app.mapper.*;
import com.moyunzhijiao.system_app.mapper.collection.ExerciseCollectionMapper;
import com.moyunzhijiao.system_app.mapper.competition.*;
import com.moyunzhijiao.system_app.mapper.exercise.*;
import com.moyunzhijiao.system_app.mapper.word.TemplateWordMapper;
import com.moyunzhijiao.system_app.utils.SubmitWritingInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ExerciseService {
    @Autowired
    SubmitWritingInfoUtil submitWritingInfoUtil;

    @Autowired
    private HomeworkMapper homeworkMapper;
    @Autowired
    private HomeworkSubmissionMapper homeworkSubmissionMapper;
    @Autowired
    ExcellentHomeworkMapper excellentHomeworkMapper;
    @Autowired
    ExcellentCompetitionMapper excellentCompetitionMapper;
    @Autowired
    CompetitionMapper competitionMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    SystemTemplateMapper systemTemplateMapper;
    @Autowired
    SystemTemplateImageMapper systemTemplateImageMapper;
    @Autowired
    FontMapper fontMapper;
    @Autowired
    HsubmissionImageMapper hsubmissionImageMapper;
    @Autowired
    RadicalMapper radicalMapper;
    @Autowired
    StructureMapper structureMapper;
    @Autowired
    HomeworkImageMapper homeworkImageMapper;
    @Autowired
    CompetitionRequirementsMapper competitionRequirementsMapper;
    @Autowired
    CompetitionRulesMapper competitionRulesMapper;
    @Autowired
    CompetitionSubmissionMapper competitionSubmissionMapper;
    @Autowired
    CsubmissionImageMapper csubmissionImageMapper;
    @Autowired
    ExerciseCollectionMapper exerciseCollectionMapper;
    @Autowired
    CharacterAnalysisMapper characterAnalysisMapper;
    @Autowired
    StrokeAnalysisMapper strokeAnalysisMapper;


    // 获取学校练习
    public List<ExerciseInfo> getSchoolExercise(Integer userId, String type, Integer pageNumber, Integer pageSize) {
        // 创建查询条件
        QueryWrapper<HomeworkSubmission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId);

        // 从数据库中获取符合条件的作业提交信息
        List<HomeworkSubmission> submissions = homeworkSubmissionMapper.selectList(queryWrapper);

        // 将 HomeworkSubmission 转换为 ExerciseInfo
        List<ExerciseInfo> exerciseInfos = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (HomeworkSubmission submission : submissions) {
            Homework homework = homeworkMapper.selectById(submission.getHomeworkId());
            if (homework != null && homework.getIsSelf() == 0) { // 只获取学校作业
                boolean isFinish = submission.getState().equals(1);
                boolean cutoff = false;
                if (homework.getDeadline() != null) {
                    cutoff = LocalDateTime.now().isAfter(LocalDateTime.parse(homework.getDeadline(), formatter));
                }
                Integer systemScore = submission.getSystemScore() != null ? submission.getSystemScore() : 0;
                if ((type.equals("finish") && isFinish) || (type.equals("unfinish") && !isFinish)) {
                    exerciseInfos.add(new ExerciseInfo(homework.getId(), "学校作业", cutoff, homework.getName(), homework.getCreatedTime(), homework.getDeadline(), systemScore, isFinish));
                }
            }
        }


        // 分页处理
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, exerciseInfos.size());

        // 检查索引有效性
        if (start >= exerciseInfos.size()) {
            // 如果结果为空或起始索引超出范围，返回空列表
            return new ArrayList<>();
        }

        return exerciseInfos.subList(start, end);
    }


    // 获取自我练习
    public List<ExerciseInfo> getSelfExercise(Integer userId, String type, Integer pageNumber, Integer pageSize) {
        // 创建查询条件
        QueryWrapper<HomeworkSubmission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId);

        // 从数据库中获取符合条件的作业提交信息
        List<HomeworkSubmission> submissions = homeworkSubmissionMapper.selectList(queryWrapper);

        // 将 HomeworkSubmission 转换为 ExerciseInfo
        List<ExerciseInfo> exerciseInfos = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (HomeworkSubmission submission : submissions) {
            Homework homework = homeworkMapper.selectById(submission.getHomeworkId());
            if (homework != null && homework.getIsSelf() == 1) { // 只获取自我练习
                boolean isFinish = submission.getState().equals(1);
                boolean cutoff = false;
                Integer systemScore = submission.getSystemScore() != null ? submission.getSystemScore() : 0;
                if ((type.equals("finish") && isFinish) || (type.equals("unfinish") && !isFinish)) {
                    exerciseInfos.add(new ExerciseInfo(homework.getId(), "自我练习", cutoff, homework.getName(), homework.getCreatedTime(), homework.getDeadline(), systemScore, isFinish));
                }
            }
        }

        // 分页处理
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, exerciseInfos.size());

        // 检查索引有效性
        if (start >= exerciseInfos.size()) {
            // 如果结果为空或起始索引超出范围，返回空列表
            return new ArrayList<>();
        }

        return exerciseInfos.subList(start, end);
    }


    // 获取优秀作品
    public List<ExcellentWorkInfo> getExcellentExercise(Integer pageNumber, Integer pageSize) {
        // 获取优秀作业
        List<ExcellentHomework> excellentHomeworks = excellentHomeworkMapper.selectList(null);
        List<ExcellentWorkInfo> homeworkInfos = excellentHomeworks.stream()
                .map(excellentHomework -> {
                    Homework homework = homeworkMapper.selectById(excellentHomework.getSubmissionsId());
                    if (homework != null) {
                        Teacher teacher = teacherMapper.selectById(excellentHomework.getRecommenderId());
                        String recommenderName = (teacher != null) ? teacher.getName() : "未知教师";
                        return new ExcellentWorkInfo(
                                excellentHomework.getSubmissionsId(),
                                "学校作品",
                                recommenderName,
                                homework.getName(),
                                homework.getCreatedTime().toString()
                        );
                    }
                    return null;
                })
                .filter(excellentWorkInfo -> excellentWorkInfo != null)
                .collect(Collectors.toList());

        // 获取优秀竞赛
        List<ExcellentCompetition> excellentCompetitions = excellentCompetitionMapper.selectList(null);
        List<ExcellentWorkInfo> competitionInfos = excellentCompetitions.stream()
                .map(excellentCompetition -> {
                    Competition competition = competitionMapper.selectById(excellentCompetition.getSubmissionsId());
                    if (competition != null) {
                        return new ExcellentWorkInfo(
                                excellentCompetition.getSubmissionsId(),
                                "竞赛作品",
                                "竞赛",
                                competition.getName(),
                                competition.getCreatedTime().toString()
                        );
                    }
                    return null;
                })
                .filter(excellentWorkInfo -> excellentWorkInfo != null)
                .collect(Collectors.toList());

        // 合并结果
        homeworkInfos.addAll(competitionInfos);

        // 分页处理
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, homeworkInfos.size());

        // 检查索引有效性
        if (start >= homeworkInfos.size()) {
            // 如果结果为空或起始索引超出范围，返回空列表
            return new ArrayList<>();
        }

        return homeworkInfos.subList(start, end);
    }


    // 获取系统模板
    public List<SystemTemplateInfo> getSystemTemplate(Integer pageNumber, Integer pageSize) {
        // 从数据库中获取所有系统模板信息
        List<SystemTemplate> templates = systemTemplateMapper.selectList(null);

        // 将 SystemTemplate 转换为 SystemTemplateInfo
        List<SystemTemplateInfo> templateInfos = templates.stream()
                .map(template -> {
                    // 从 system_template_image 表中获取内容图片
                    SystemTemplateImage templateImage = systemTemplateImageMapper.selectFirstByTemplateId(template.getId());
                    String content = (templateImage != null) ? templateImage.getPictureUrl() : null;

                    // 从 font 表中获取字体信息
                    Font font = fontMapper.selectById(template.getFontId());

                    return new SystemTemplateInfo(
                            template.getId(),
                            template.getType(),
                            template.getName(),
                            content,
                            font.getName()
                    );
                })
                .collect(Collectors.toList());

        // 分页处理
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, templateInfos.size());

        // 检查索引有效性
        if (start >= templateInfos.size()) {
            // 如果结果为空或起始索引超出范围，返回空列表
            return new ArrayList<>();
        }

        return templateInfos.subList(start, end);
    }


    // 获取已有练习
    public List<SystemTemplateInfo> getExistExercise(Integer userId, Integer pageNumber, Integer pageSize) {
        // 从 homework_submission 表中查找用户提交且批改过的作业
        List<HomeworkSubmission> submissions = homeworkSubmissionMapper.selectList(
                new QueryWrapper<HomeworkSubmission>()
                        .eq("student_id", userId)
                        .eq("state", 1)
        );

        // 获取对应的 homework_id 列表
        List<Integer> homeworkIds = submissions.stream()
                .map(HomeworkSubmission::getHomeworkId)
                .collect(Collectors.toList());

        // 从 homework 表中获取对应的作业信息
        List<Homework> homeworks = homeworkMapper.selectBatchIds(homeworkIds);

        // 将 Homework 转换为 SystemTemplateInfo
        List<SystemTemplateInfo> templateInfos = homeworks.stream()
                .map(homework -> {
                    // 从 homework_image 表中获取内容图片
                    HsubmissionImage homeworkImage = hsubmissionImageMapper.selectById(homework.getId());
                    String content = (homeworkImage != null) ? homeworkImage.getPictureUrl() : null;

                    // 从 font 表中获取字体信息
                    Font font = fontMapper.selectById(homework.getFontId());

                    return new SystemTemplateInfo(
                            homework.getId(),
                            homework.getType(),
                            homework.getName(),
                            content,
                            font.getName()
                    );
                })
                .collect(Collectors.toList());

        // 分页处理
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, templateInfos.size());

        // 检查索引有效性
        if (start >= templateInfos.size()) {
            // 如果结果为空或起始索引超出范围，返回空列表
            return new ArrayList<>();
        }

        return templateInfos.subList(start, end);
    }

    @Autowired
    TemplateWordMapper templateWordMapper;

    // 获取样本字
    public List<WordListInfo> getTemplateWord(String search, String radical, String structure, String typeface, Integer pageNumber, Integer pageSize) {
        // 创建查询条件
        QueryWrapper<TemplateWord> queryWrapper = new QueryWrapper<>();
        if (search != null && !search.isEmpty()) {
            queryWrapper.like("name", search);
        }

        // 从 sample_word 表中获取符合条件的样本字
        List<TemplateWord> templateWords = templateWordMapper.selectList(queryWrapper);

        // 将 SampleWord 转换为 WordListInfo，并进行名称匹配
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

        // 检查索引有效性
        if (start >= wordListInfos.size()) {
            // 如果结果为空或起始索引超出范围，返回空列表
            return new ArrayList<>();
        }

        return wordListInfos.subList(start, end);
    }


    // 获取所选单字
    public WordListInfo getSelectWord(Integer sampleWordId) {
        // 通过 SampleWord 的 Id 查找对应的样本字
        TemplateWord templateWord = templateWordMapper.selectById(sampleWordId);
        if (templateWord == null) {
            return null; // 如果找不到对应的样本字，返回 null
        }

        // 查找对应的偏旁和结构名称
        Radical radicalEntity = radicalMapper.selectById(templateWord.getRadicalId());
        Structure structureEntity = structureMapper.selectById(templateWord.getStructureId());
        String radicalName = radicalEntity != null ? radicalEntity.getName() : null;
        String structureName = structureEntity != null ? structureEntity.getName() : null;

        // 创建并返回 WordListInfo 对象
        return new WordListInfo(templateWord.getId(), templateWord.getContent(), templateWord.getName()/*, radicalName, structureName*/);
    }


    // 获取学校已完成详情
    public SchoolFinishExerciseDetailInfo getSchoolFinish(Integer userId, Integer exerciseId) {
        // 1. 根据 userId 和 state = 1 从 HomeworkSubmission 中查找 homework_id
        QueryWrapper<HomeworkSubmission> submissionQueryWrapper = new QueryWrapper<>();
        submissionQueryWrapper.eq("student_id", userId)
                .eq("homework_id", exerciseId)
                .eq("state", 1);
        HomeworkSubmission submission = homeworkSubmissionMapper.selectOne(submissionQueryWrapper);

        if (submission == null) {
            return null; // 作业未找到或未完成
        }

        // 2. 根据 submission.getHomeworkId 从 Homework 表中查找作业详情
        Homework homework = homeworkMapper.selectById(submission.getHomeworkId());
        if (homework == null || homework.getIsSelf() == 1) {
            return null; // 作业未找到或不是学校练习
        }

        Boolean ifCollect = isExerciseCollected(userId, exerciseId, "学校作业");

        // 3. 获取 CharacterAnalysis 列表
        QueryWrapper<CharacterAnalysis> characterQueryWrapper = new QueryWrapper<>();
        characterQueryWrapper.eq("submission_id", submission.getId());
        List<CharacterAnalysis> characterAnalyses = characterAnalysisMapper.selectList(characterQueryWrapper);

        // 4. 构造 WordInfo 对象
        List<WordInfo> wordInfos = new ArrayList<>();
        for (CharacterAnalysis character : characterAnalyses) {
            // 获取关联的 StrokeAnalysis 列表
            QueryWrapper<StrokeAnalysis> strokeQueryWrapper = new QueryWrapper<>();
            strokeQueryWrapper.eq("character_analysis_id", character.getId());
            List<StrokeAnalysis> strokes = strokeAnalysisMapper.selectList(strokeQueryWrapper);

            // 根据 character.getName() 从 template_word 表中查找对应的模板字
            QueryWrapper<TemplateWord> templateQueryWrapper = new QueryWrapper<>();
            templateQueryWrapper.eq("name", character.getName());
            TemplateWord templateWord = templateWordMapper.selectOne(templateQueryWrapper);

            // 获取模板字的图片 URL
            String templateWordContent = (templateWord != null) ? templateWord.getContent() : "default_template_word_url";

            // 构造 WordInfo 对象
            WordInfo wordInfo = new WordInfo(
                    character.getPicture(),
                    strokes.stream().map(StrokeAnalysis::getPicture).collect(Collectors.toList()),
                    character.getScore(),
                    templateWordContent, // 使用模板字的图片 URL
                    character.getEvaluation()
            );
            wordInfos.add(wordInfo);
        }

        // 5. 构造 SubmitWritingInfo 对象
        SubmitWritingInfo submitWritingInfo = submitWritingInfoUtil.createSubmitWritingInfo(submission, wordInfos);

        // 6. 构造 SchoolFinishExerciseDetailInfo 对象
        SchoolFinishExerciseDetailInfo detailInfo = new SchoolFinishExerciseDetailInfo(
                homework.getId(),
                homework.getName(),
                homework.getTarget(), // 类型
                ifCollect, // 假设没有收藏功能
                homework.getDifficulty(),
                homework.getWordCount(),
                fontMapper.selectById(homework.getFontId()).getName(), // 从 font 表中获取字体信息
                homework.getType() + homework.getDetailType(), // 假设 type=0 为练习，其他为作业
                homework.getRequirements(),
                homeworkImageMapper.selectById(homework.getId()).getPictureUrl(), // 从 homeworkImage 表中获取样例图
                Arrays.asList(submitWritingInfo), // 获取 SubmitWritingInfo 列表并填充
                submission.getSystemScore(),
                submission.getSystemFeedback(),
                submission.getTeacherFeedback()
        );

        return detailInfo;
    }



    // 判断学生是否收藏了某个练习
    public boolean isExerciseCollected(Integer userId, Integer submissionId, String type) {
        QueryWrapper<ExerciseCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId)
                .eq("submission_id", submissionId)
                .eq("type", type);
        ExerciseCollection collection = exerciseCollectionMapper.selectOne(queryWrapper);

        return collection != null; // 如果找到匹配的收藏记录，返回 true，否则返回 false
    }


    // 获取学校未完成详情
    public SchoolUnfinishExerciseDetailInfo getSchoolUnfinish(Integer userId, Integer exerciseId) {
        // 1. 根据 userId 和 state = 0 从 HomeworkSubmission 中查找 homework_id
        QueryWrapper<HomeworkSubmission> submissionQueryWrapper = new QueryWrapper<>();
        submissionQueryWrapper.eq("student_id", userId)
                .eq("homework_id", exerciseId)
                .eq("state", 0);
        HomeworkSubmission submission = homeworkSubmissionMapper.selectOne(submissionQueryWrapper);

        if (submission == null) {
            return null; // 作业未找到或未开始
        }

        System.out.println("homework_id"+exerciseId+submission.getHomeworkId());
        // 2. 根据 submission.getHomeworkId 从 Homework 表中查找作业详情
        Homework homework = homeworkMapper.selectById(submission.getHomeworkId());
        if (homework == null || homework.getIsSelf() == 1) {
            return null; // 作业未找到或不是学校练习
        }

        // 检查 homework.getDeadline() 是否为 null
        boolean cutoff = false;
        System.out.println("Current time: " + LocalDateTime.now());
        System.out.println("Homework deadline: " + homework.getDeadline());
        if (homework.getDeadline() != null) {
            cutoff = LocalDateTime.now().isAfter(
                    LocalDateTime.parse(homework.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            );
        }

        // 检查其他字段是否为 null 并提供默认值
        Integer difficulty = (homework.getDifficulty() != null) ? homework.getDifficulty() : 0; // 提供默认值0
        Integer wordCount = (homework.getWordCount() != null) ? homework.getWordCount() : 0; // 提供默认值"0"
        String type = (homework.getType() != null) ? homework.getType() : ""; // 提供默认值""
        String requirements = (homework.getRequirements() != null) ? homework.getRequirements() : ""; // 提供默认值""

        // 3. 构造 SchoolUnfinishExerciseDetailInfo 对象
        SchoolUnfinishExerciseDetailInfo detailInfo = new SchoolUnfinishExerciseDetailInfo(
                homework.getId(),
                homework.getName(),
                homework.getTarget(), // 类型
                false, // 假设没有收藏功能
                difficulty,
                wordCount,
                fontMapper.selectById(homework.getFontId()).getName(), // 从 font 表中获取字体信息
                type + homework.getDetailType(), // 假设 type=0 为练习，其他为作业
                (homework.getDeadline() != null) ? homework.getDeadline().toString() : "", // 截止时间
                requirements,
                homeworkImageMapper.selectById(homework.getId()).getPictureUrl(), // 从 homeworkImage 表中获取样例图
                new String[]{hsubmissionImageMapper.selectById(submission.getHomeworkId()).getPictureUrl()}, // 提交的图
                cutoff
        );

        return detailInfo;
    }



    // 获取自我已完成详情
    public SelfFinishExerciseDetailInfo getMineFinish(Integer userId, Integer exerciseId) {
        // 1. 根据 userId 和 state = 1 从 HomeworkSubmission 中查找 homework_id
        QueryWrapper<HomeworkSubmission> submissionQueryWrapper = new QueryWrapper<>();
        submissionQueryWrapper.eq("student_id", userId)
                .eq("homework_id", exerciseId)
                .eq("state", 1);
        HomeworkSubmission submission = homeworkSubmissionMapper.selectOne(submissionQueryWrapper);

        if (submission == null) {
            return null; // 作业未找到或未完成
        }

        // 2. 根据 submission.getHomeworkId 从 Homework 表中查找作业详情
        Homework homework = homeworkMapper.selectById(submission.getHomeworkId());
        if (homework == null || homework.getIsSelf() == 0) {
            return null; // 作业未找到或不是自我练习
        }

        Boolean ifCollect = isExerciseCollected(userId, exerciseId, "自我练习");

        // 3. 获取 CharacterAnalysis 列表
        QueryWrapper<CharacterAnalysis> characterQueryWrapper = new QueryWrapper<>();
        characterQueryWrapper.eq("submission_id", submission.getId());
        List<CharacterAnalysis> characterAnalyses = characterAnalysisMapper.selectList(characterQueryWrapper);

        // 4. 构造 WordInfo 对象
        List<WordInfo> wordInfos = new ArrayList<>();
        for (CharacterAnalysis character : characterAnalyses) {
            // 获取关联的 StrokeAnalysis 列表
            QueryWrapper<StrokeAnalysis> strokeQueryWrapper = new QueryWrapper<>();
            strokeQueryWrapper.eq("character_analysis_id", character.getId());
            List<StrokeAnalysis> strokes = strokeAnalysisMapper.selectList(strokeQueryWrapper);

            // 构造 WordInfo 对象
            WordInfo wordInfo = new WordInfo(
                    character.getPicture(),
                    strokes.stream().map(StrokeAnalysis::getPicture).collect(Collectors.toList()),
                    character.getScore(),
                    character.getName(),
                    character.getEvaluation()
            );
            wordInfos.add(wordInfo);
        }

        // 5. 构造 SubmitWritingInfo 对象
        SubmitWritingInfo submitWritingInfo = submitWritingInfoUtil.createSubmitWritingInfo(submission, wordInfos);

        // 6. 构造 SelfFinishExerciseDetailInfo 对象
        SelfFinishExerciseDetailInfo detailInfo = new SelfFinishExerciseDetailInfo(
                homework.getId(),
                homework.getName(),
                ifCollect, // 收藏
                fontMapper.selectById(homework.getFontId()).getName(), // 从 font 表中获取字体信息
                homeworkImageMapper.selectById(homework.getId()).getPictureUrl(), // 从 homeworkImage 表中获取样例图
                Arrays.asList(submitWritingInfo), // 获取 SubmitWritingInfo 列表并填充
                submission.getSystemScore(),
                submission.getSystemFeedback()
        );

        return detailInfo;
    }


    // 获取自我未完成详情
    public SelfUnfinishExerciseDetailInfo getMineUnfinish(Integer userId, Integer exerciseId) {
        // 1. 根据 userId 和 state = 0 从 HomeworkSubmission 中查找 homework_id
        QueryWrapper<HomeworkSubmission> submissionQueryWrapper = new QueryWrapper<>();
        submissionQueryWrapper.eq("student_id", userId)
                .eq("homework_id", exerciseId)
                .eq("state", 0);
        HomeworkSubmission submission = homeworkSubmissionMapper.selectOne(submissionQueryWrapper);

        if (submission == null) {
            return null; // 作业未找到或未开始
        }

        // 2. 根据 submission.getHomeworkId 从 Homework 表中查找作业详情
        Homework homework = homeworkMapper.selectById(submission.getHomeworkId());
        if (homework == null || homework.getIsSelf() == 0) {
            return null; // 作业未找到或不是自我练习
        }

        // 3. 获取提交的图
        List<String> submittedImages = new ArrayList<>();
        HsubmissionImage hsubmissionImage = hsubmissionImageMapper.selectById(submission.getHomeworkId());
        if (hsubmissionImage != null) {
            String submittedImageUrl = hsubmissionImage.getPictureUrl();
            if (submittedImageUrl != null) {
                submittedImages.add(submittedImageUrl);
            }
        }
        // 4. 构造 SelfUnfinishExerciseDetailInfo 对象
        SelfUnfinishExerciseDetailInfo detailInfo = new SelfUnfinishExerciseDetailInfo(
                homework.getId(),
                homework.getName(),
                false, // 假设没有收藏功能
                fontMapper.selectById(homework.getFontId()).getName(), // 从 font 表中获取字体信息
                homework.getRequirements(),
                homeworkImageMapper.selectById(homework.getId()).getPictureUrl(), // 从 homeworkImage 表中获取样例图
                submittedImages
        );

        return detailInfo;
    }


    // 获取优秀学校练习详情
    public ExcellentHomeworkInfo getExcellentHomework(Integer userId, Integer exerciseId) {
        // 获取优秀作业
        ExcellentHomework excellentHomework = excellentHomeworkMapper.selectById(exerciseId);
        if (excellentHomework != null) {
            Homework homework = homeworkMapper.selectById(excellentHomework.getSubmissionsId());
            if (homework != null) {
                Teacher teacher = teacherMapper.selectById(excellentHomework.getRecommenderId());
                String recommenderName = (teacher != null) ? teacher.getName() : "未知教师";

                // 查询系统评论和教师评论
                HomeworkSubmission homeworkSubmission = homeworkSubmissionMapper.selectOne(
                        new QueryWrapper<HomeworkSubmission>().eq("id", exerciseId)
                );
                String systemComment = (homeworkSubmission != null) ? homeworkSubmission.getSystemFeedback() : "";
                String teacherComment = (homeworkSubmission != null) ? homeworkSubmission.getTeacherFeedback() : "";

                // 检查 homework.getDifficulty() 和 homework.getWordCount() 是否为 null
                int difficulty = (homework.getDifficulty() != null) ? homework.getDifficulty() : 0; // 提供默认值 0
                int wordCount = (homework.getWordCount() != null) ? homework.getWordCount() : 0; // 提供默认值 0
                String type = (homework.getType() != null) ? homework.getType() : ""; // 提供默认值 ""
                String requirements = (homework.getRequirements() != null) ? homework.getRequirements() : ""; // 提供默认值 ""

                boolean ifCollect = isExerciseCollected(userId, exerciseId, "优秀学校作品");

                // 获取 CharacterAnalysis 列表
                QueryWrapper<CharacterAnalysis> characterQueryWrapper = new QueryWrapper<>();
                characterQueryWrapper.eq("submission_id", homeworkSubmission.getId());
                List<CharacterAnalysis> characterAnalyses = characterAnalysisMapper.selectList(characterQueryWrapper);

                // 构造 WordInfo 对象
                List<WordInfo> wordInfos = new ArrayList<>();
                for (CharacterAnalysis character : characterAnalyses) {
                    // 获取关联的 StrokeAnalysis 列表
                    QueryWrapper<StrokeAnalysis> strokeQueryWrapper = new QueryWrapper<>();
                    strokeQueryWrapper.eq("character_analysis_id", character.getId());
                    List<StrokeAnalysis> strokes = strokeAnalysisMapper.selectList(strokeQueryWrapper);

                    // 构造 WordInfo 对象
                    WordInfo wordInfo = new WordInfo(
                            character.getPicture(),
                            strokes.stream().map(StrokeAnalysis::getPicture).collect(Collectors.toList()),
                            character.getScore(),
                            character.getName(),
                            character.getEvaluation()
                    );
                    wordInfos.add(wordInfo);
                }

                // 构造 SubmitWritingInfo 对象
                SubmitWritingInfo submitWritingInfo = submitWritingInfoUtil.createSubmitWritingInfo(homeworkSubmission, wordInfos);

                return new ExcellentHomeworkInfo(
                        excellentHomework.getSubmissionsId(),
                        homework.getName(),
                        recommenderName,
                        ifCollect,  // 收藏
                        difficulty,
                        wordCount,
                        fontMapper.selectById(homework.getFontId()).getName(),
                        type,
                        requirements,
                        homeworkImageMapper.selectById(homework.getId()).getPictureUrl(),
                        Arrays.asList(submitWritingInfo),
                        homeworkSubmission.getSystemScore(),
                        systemComment,
                        teacherComment
                );
            }
        }
        return null; // 作业未找到或未完成
    }


    // 获取优秀竞赛练习详情
    public ExcellentCompetitionInfo getExcellentCompetition(Integer userId, Integer competitionId) {
        // 获取竞赛
        Competition competition = competitionMapper.selectById(competitionId);
        if (competition != null) {
            System.out.println("competition find");
            // 获取竞赛内容
            List<CompetitionContentInfo> contentInfos = competitionSubmissionMapper.selectList(
                    new QueryWrapper<CompetitionSubmissions>().eq("competition_id", competitionId)
            ).stream().map(content -> {
                // 获取提交图片信息
                System.out.println("getCompetitionId " + content.getCompetitionId());
                List<CsubmissionImage> images = csubmissionImageMapper.selectList(
                        new QueryWrapper<CsubmissionImage>().eq("submission_id", content.getCompetitionId())
                );
                if (images.isEmpty()) {
                    // 处理空值情况
                    System.out.println("image 空值");
                    return null;
                }
                System.out.println("images find");

                // 获取 CharacterAnalysis 列表
                QueryWrapper<CharacterAnalysis> characterQueryWrapper = new QueryWrapper<>();
                characterQueryWrapper.eq("submission_id", content.getCompetitionId());
                List<CharacterAnalysis> characterAnalyses = characterAnalysisMapper.selectList(characterQueryWrapper);

                // 构造 WordInfo 对象
                List<WordInfo> wordInfos = new ArrayList<>();
                for (CharacterAnalysis character : characterAnalyses) {
                    // 获取关联的 StrokeAnalysis 列表
                    QueryWrapper<StrokeAnalysis> strokeQueryWrapper = new QueryWrapper<>();
                    strokeQueryWrapper.eq("character_analysis_id", character.getId());
                    List<StrokeAnalysis> strokes = strokeAnalysisMapper.selectList(strokeQueryWrapper);

                    // 构造 WordInfo 对象
                    WordInfo wordInfo = new WordInfo(
                            character.getPicture(),
                            strokes.stream().map(StrokeAnalysis::getPicture).collect(Collectors.toList()),
                            character.getScore(),
                            character.getName(),
                            character.getEvaluation()
                    );
                    wordInfos.add(wordInfo);
                }

                // 构造 SubmitWritingInfo 对象列表
                List<SubmitWritingInfo> submitWritingInfos = images.stream().map(image -> submitWritingInfoUtil.createSubmitWritingInfo(image, wordInfos)).collect(Collectors.toList());

                return new CompetitionContentInfo(
                        content.getCompetitionId(),
                        content.getAverageFinalScore() != null ? content.getAverageFinalScore() : 0,
                        content.getInitialRank() != null ? content.getInitialRank() : 0,
                        content.getInitialEvaluation() != null ? content.getInitialEvaluation() : "",
                        content.getSystemEvaluation() != null ? content.getSystemEvaluation() : "",
                        submitWritingInfos
                );
            }).filter(Objects::nonNull).collect(Collectors.toList());

            Boolean ifCollect = isExerciseCollected(userId, competitionId, "优秀竞赛作品");

            return new ExcellentCompetitionInfo(
                    competition.getId(),
                    competition.getName(),
                    "竞赛",
                    ifCollect,  // 收藏
                    contentInfos
            );
        }
        return null;  // 如果未找到对应的竞赛，返回 null
    }


    // 上传练习
    public boolean uploadExercise(UploadDTO uploadDTO) {
        // 检查是否存在相同的student_id和homework_id的记录
        List<HomeworkSubmission> existingSubmissions = homeworkSubmissionMapper.findByStudentIdAndHomeworkId(uploadDTO.getUserId(), uploadDTO.getExerciseId());

        if (existingSubmissions != null && !existingSubmissions.isEmpty()) {
            for (HomeworkSubmission existingSubmission : existingSubmissions) {
                // 更新状态为1
                UpdateWrapper<HomeworkSubmission> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("student_id", uploadDTO.getUserId()).eq("homework_id", uploadDTO.getExerciseId());
                existingSubmission.setState(1);
                homeworkSubmissionMapper.update(existingSubmission, updateWrapper);

                // 插入或替换图片记录
                for (String imageUrl : uploadDTO.getContent()) {
                    QueryWrapper<HsubmissionImage> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("submission_id", existingSubmission.getId());
                    HsubmissionImage existingImage = hsubmissionImageMapper.selectOne(queryWrapper);

                    if (existingImage != null) {
                        // 替换图片记录
                        existingImage.setPictureUrl(imageUrl);
                        hsubmissionImageMapper.updateById(existingImage);
                    } else {
                        // 插入新的图片记录
                        HsubmissionImage hsubmissionImage = new HsubmissionImage();
                        hsubmissionImage.setSubmissionId(existingSubmission.getId());
                        hsubmissionImage.setPictureUrl(imageUrl);
                        hsubmissionImageMapper.insert(hsubmissionImage);
                    }
                }
            }
            return true;
        } else {
            // 报错
            throw new RuntimeException("没有找到对应的练习记录");
        }
    }



    // 创建练习
    public boolean createExercise(CreateDTO createDTO) {
        // 查找字体ID
        QueryWrapper<Font> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", createDTO.getTypeface());
        Font font = fontMapper.selectOne(queryWrapper);
        if (font == null) {
            return false; // 字体名称不存在
        }

        // 创建新的作业对象
        Homework homework = new Homework();
        homework.setName(createDTO.getTitle());
        homework.setFontId(font.getId());
        homework.setIsSelf(1);

        // 插入作业记录
        int homeworkResult = homeworkMapper.insert(homework);

        if (homeworkResult > 0) {
            // 插入作业图片记录
            HomeworkImage homeworkImage = new HomeworkImage();
            homeworkImage.setHomeworkId(homework.getId());
            homeworkImage.setPictureUrl(createDTO.getContent());
            homeworkImageMapper.insert(homeworkImage);

            // 插入作业提交记录
            HomeworkSubmission homeworkSubmission = new HomeworkSubmission();
            homeworkSubmission.setHomeworkId(homework.getId());
            homeworkSubmission.setStudentId(createDTO.getUserId());
            homeworkSubmission.setSystemScore(null);
            homeworkSubmission.setSystemFeedback(null);
            homeworkSubmission.setTeacherScore(null);
            homeworkSubmission.setTeacherFeedback(null);
            homeworkSubmission.setSubmitedTime(null);
            homeworkSubmission.setState(0);
            homeworkSubmission.setReviewed(0);
            homeworkSubmissionMapper.insert(homeworkSubmission);

            return true;
        } else {
            return false;
        }
    }


    // 生成专项练习
    public String generateExercise(WordListInfo[] wordListInfos) {
        return "https://dummyimage.com/120x120";
    }



    // 生成练习综合
    public String generateExercise2(Generate generate) {
        return "https://dummyimage.com/120x120";
    }
}




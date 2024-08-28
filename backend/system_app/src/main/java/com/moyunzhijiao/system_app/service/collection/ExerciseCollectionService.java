package com.moyunzhijiao.system_app.service.collection;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_app.controller.dto.CollectDTO;
import com.moyunzhijiao.system_app.controller.dto.fonted.CollectInfo;
import com.moyunzhijiao.system_app.entity.competition.Competition;
import com.moyunzhijiao.system_app.entity.exercise.Homework;
import com.moyunzhijiao.system_app.entity.collection.ExerciseCollection;
import com.moyunzhijiao.system_app.mapper.competition.CompetitionMapper;
import com.moyunzhijiao.system_app.mapper.exercise.HomeworkMapper;
import com.moyunzhijiao.system_app.mapper.collection.ExerciseCollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseCollectionService extends ServiceImpl<ExerciseCollectionMapper, ExerciseCollection> {
    @Autowired
    ExerciseCollectionMapper exerciseCollectionMapper;
    @Autowired
    HomeworkMapper homeworkMapper;
    @Autowired
    CompetitionMapper competitionMapper;

    // 获取用户练习收藏
    public List<CollectInfo> getExerciseCollect(Integer userId, Integer pageNumber, Integer pageSize) {
        // 通过用户ID找到收藏表中的 submissionId 和 type
        QueryWrapper<ExerciseCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId);
        List<ExerciseCollection> collections = exerciseCollectionMapper.selectList(queryWrapper);

        System.out.println("获取的数据"+collections);

        List<CollectInfo> results = new ArrayList<>();
        for (ExerciseCollection collection : collections) {
            System.out.println("获取的SubmissionId"+collection.getSubmissionId());
            CollectInfo result = getSubmissionByType(collection.getSubmissionId(), collection.getType());
            if (result != null) {
                results.add(result);
            }
        }

        // 分页处理
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, results.size());

        // 检查索引有效性
        if (start >= results.size()) {
            // 如果结果为空或起始索引超出范围，返回空列表
            return new ArrayList<>();
        }

        return results.subList(start, end);


    }

    private CollectInfo getSubmissionByType(Integer submissionId, String type) {
        System.out.println("获取的SubmissionId"+submissionId+"类型"+type);
        return switch (type) {
            case "学校作业", "自我练习" -> getSubmission1(submissionId, type);
            case "优秀竞赛作品" -> getSubmission2(submissionId, type);
            case "优秀学校作品" -> getSubmission3(submissionId, type);
            default -> null;
        };
    }


    private CollectInfo getSubmission1(Integer submissionId, String type) {
        System.out.println("获取的SubmissionId"+submissionId+"类型"+type);
        if (submissionId != null) {
            Homework homework = homeworkMapper.selectById(submissionId);
            if (homework != null) {
                System.out.println("获取的 create_time: " + homework.getCreatedTime() );
                return new CollectInfo(submissionId, type, homework.getName(), homework.getCreatedTime());
            } else {
                System.out.println("未找到对应的 homework 记录");
            }
        } else {
            System.out.println("未找到对应的 submissionId");
        }
        return null;
    }


    private CollectInfo getSubmission2(Integer submissionId, String type) {
        System.out.println("获取的SubmissionId"+submissionId+"类型"+type);
        if (submissionId != null) {
            Competition competition = competitionMapper.selectById(submissionId);
            if (competition != null) {
                System.out.println("获取的 create_time: " + competition.getCreatedTime() );
                return new CollectInfo(submissionId, type, competition.getName(), competition.getCreatedTime());
            } else {
                System.out.println("未找到对应的 competition 记录");
            }
        } else {
            System.out.println("未找到对应的 submissionId");
        }
        return null;
    }


    private CollectInfo getSubmission3(Integer submissionId, String type) {
        System.out.println("获取的SubmissionId"+submissionId+"类型"+type);
        if (submissionId != null) {
            Homework homework = homeworkMapper.selectById(submissionId);
            if (homework != null) {
                System.out.println("获取的 create_time: " + homework.getCreatedTime() );
                return new CollectInfo(submissionId, type, homework.getName(), homework.getCreatedTime());
            } else {
                System.out.println("未找到对应的 优秀homework 记录");
            }
        } else {
            System.out.println("未找到对应的 submissionId");
        }
        return null;
    }


    // 处理练习收藏
    public boolean handleExerciseCollect(CollectDTO collectDTO) {
        if (collectDTO.getNewCollect()) {
            return addExerciseCollect(collectDTO);
        } else {
            return removeExerciseCollect(collectDTO);
        }
    }

    public boolean addExerciseCollect(CollectDTO collectDTO) {
        String type = "";
        switch (collectDTO.getCollectType()){
            case "SelfExercise":
                type = "自我练习";
                break;
            case "SchoolExercise":
                type = "学校作业";
                break;
            case "ExcellentHomework":
                type = "优秀学校作品";
                break;
            case "ExcellentCompetition":
                type = "优秀竞赛作品";
                break;
//            case "Competition":
//                type = "优秀竞赛作品";
//                break;
        }

        // 检查是否已经存在
        Long count = exerciseCollectionMapper.selectCount(
                new QueryWrapper<ExerciseCollection>()
                        .eq("student_id", collectDTO.getUserId())
                        .eq("submission_id", collectDTO.getCollectId())
        );
        if (count > 0) {
            return false; // 已经存在，不重复添加
        }

        ExerciseCollection exerciseCollection = new ExerciseCollection();
        exerciseCollection.setId(collectDTO.getUserId());
        exerciseCollection.setSubmissionId(collectDTO.getCollectId());
        exerciseCollection.setType(type);
        return exerciseCollectionMapper.insert(exerciseCollection) > 0;
    }

    public boolean removeExerciseCollect(CollectDTO collectDTO) {
        return exerciseCollectionMapper.delete(
                new QueryWrapper<ExerciseCollection>()
                        .eq("student_id", collectDTO.getUserId())
                        .eq("submission_id", collectDTO.getCollectId())
        ) > 0;
    }
}




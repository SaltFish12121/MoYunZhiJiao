package com.moyunzhijiao.system_app.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_app.controller.dto.SignUpDTO;
import com.moyunzhijiao.system_app.controller.dto.SubmitDTO;
import com.moyunzhijiao.system_app.controller.dto.fonted.SubmitWritingInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.competition.CompetitionDetailInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.competition.CompetitionInfo;
import com.moyunzhijiao.system_app.entity.Font;
import com.moyunzhijiao.system_app.entity.competition.*;
import com.moyunzhijiao.system_app.mapper.FontMapper;
import com.moyunzhijiao.system_app.mapper.collection.CollectionMapper;
import com.moyunzhijiao.system_app.mapper.competition.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompetitionService extends ServiceImpl<CompetitionMapper, Competition> {
    @Autowired
    CompetitionMapper competitionMapper;
    @Autowired
    CompetitionSubmissionMapper competitionSubmissionMapper;
    @Autowired
    ParticipantMapper participantMapper;
    @Autowired
    CollectionMapper collectionMapper;
    @Autowired
    FontMapper fontMapper;
    @Autowired
    CsubmissionImageMapper csubmissionImageMapper;
    @Autowired
    CompetitionRequirementsMapper competitionRequirementsMapper;
    @Autowired
    DivisionRequirementsMapper divisionRequirementsMapper;
    @Autowired
    DivisionMapper divisionMapper;


    // 获取竞赛信息
    public List<CompetitionInfo> getCompetition(Integer userId, String type) {
        // 检查传入的类型是否有效
        if (!"finish".equals(type) && !"unfinish".equals(type)) {
            throw new IllegalArgumentException("Invalid competition type: " + type);
        }

        // 获取所有竞赛
        List<Competition> competitions = competitionMapper.selectList(null);
        List<CompetitionInfo> competitionInfos = competitions.stream()
                .map(competition -> {
                    // 检查用户是否报名
                    boolean isSignedUp = participantMapper.existsByStudentIdAndCompetitionId(userId, competition.getId());

                    // 根据竞赛状态和用户报名情况确定类型
                    String competitionType;
                    if ("已结束".equals(competition.getState())) {
                        competitionType = "结束";
                    } else if ("评阅中".equals(competition.getState())) {
                        competitionType = "已截止";
                    } else if ("准备报名中".equals(competition.getState())) {
                        competitionType = "未截止";
                    } else {
                        // 检查提交状态
                        CompetitionSubmissions submission = competitionSubmissionMapper.findByUserIdAndCompetitionId(userId, competition.getId());
                        if (submission != null) {
                            if (submission.getInitialScore() != -1) {
                                competitionType = "已提交";
                            } else {
                                competitionType = "未提交";
                            }
                        } else {
                            competitionType = "提交阶段";
                        }
                    }

                    // 获取用户在特定竞赛中的得分
                    int score = 0;
                    // 检查提交状态
                    CompetitionSubmissions submission = competitionSubmissionMapper.findByUserIdAndCompetitionId(userId, competition.getId());
                    if (submission != null && submission.getSystemScore() != null) {
                        score = submission.getSystemScore();
                    }

                    // 根据类型过滤竞赛
                    if ("finish".equals(type) && "结束".equals(competitionType)) {
                        return new CompetitionInfo(
                                competition.getId(),
                                isSignedUp ? "已报名" : "未报名",
                                competitionType,
                                competition.getName(),
                                competition.getCompetitionStartTime(),
                                competition.getCompetitionEndTime(),
                                score
                        );
                    } else if ("unfinish".equals(type) && !"结束".equals(competitionType)) {
                        return new CompetitionInfo(
                                competition.getId(),
                                isSignedUp ? "已报名" : "未报名",
                                competitionType,
                                competition.getName(),
                                competition.getCompetitionStartTime(),
                                competition.getCompetitionEndTime(),
                                score
                        );
                    }
                    return null;
                })
                .filter(competitionInfo -> competitionInfo != null)
                .collect(Collectors.toList());

        return competitionInfos;
    }


    // 获取竞赛详细信息
    public CompetitionDetailInfo getCompetitionDetail(Integer userId, Integer competitionId) {
        // 获取竞赛信息
        Competition competition = competitionMapper.selectById(competitionId);
        if (competition == null) {
            throw new IllegalArgumentException("Invalid competition ID: " + competitionId);
        }


        // 获取用户在特定竞赛中的得分和排名
        CompetitionSubmissions submission = competitionSubmissionMapper.findByUserIdAndCompetitionId(userId, competitionId);
        int score = (submission != null && submission.getSystemScore() != null) ? submission.getSystemScore() : 0;
        int rank = (submission != null && submission.getInitialRank() != null) ? submission.getInitialRank() : 0;

        // 获取用户是否收藏
//        boolean ifCollect = collectionMapper.existsByUserIdAndCompetitionId(userId, competitionId);
        boolean ifCollect = false;

        // 获取提交信息
        List<SubmitWritingInfo> submit = competitionSubmissionMapper.selectList(
                new QueryWrapper<CompetitionSubmissions>().eq("competition_id", competitionId)
        ).stream().map(content -> {
            // 获取提交图片信息
            CsubmissionImage image = csubmissionImageMapper.selectById(content.getCompetitionId());
            if (image == null) {
                return null;
            }
            return new SubmitWritingInfo(
                    image.getSubmissionId(),
                    image.getPictureUrl()
            );
        }).filter(Objects::nonNull).collect(Collectors.toList());

        // 构建竞赛详细信息对象
        String award = (submission != null) ? submission.getName() : "";
        String comment = (submission != null) ? submission.getSystemEvaluation() : "";

        CompetitionDetailInfo competitionDetailInfo = new CompetitionDetailInfo(
                competition.getId(),
                competition.getName(),
                competition.getCompetitionStartTime(),
                competition.getCompetitionEndTime(),
                "", // 不需要字体
                divisionRequirementsMapper.selectById(submission.getDivisionId()).getRequirements(), // 设置竞赛需求
                competition.getPicture(),
                submit,
                score,
                rank,
                award,
                comment,
                ifCollect
        );

        return competitionDetailInfo;
    }


    // 报名竞赛并存储数据
    public Boolean signUpCompetition(SignUpDTO signUpDTO) {
        // 使用 DivisionMapper 查询 divisionId
        Integer divisionId = divisionMapper.findDivisionIdByCompetitionId(signUpDTO.getCompetitionId());//加上id

        if (divisionId != null) {
            // 插入 Participant 记录
            Participant participant = new Participant();
            participant.setDivisionId(divisionId);
            participant.setStudentId(signUpDTO.getUserId());
            participant.setSubmissionId(0); // 假设 submission 插入后会自动生成 ID
            participant.setCompetitionId(signUpDTO.getCompetitionId());

            participantMapper.insert(participant);
            return true;
        } else {
            return false;
        }
    }


    public boolean submitCompetition(SubmitDTO submitDTO) {
        // 查找 divisionId
        Integer divisionId = divisionMapper.findDivisionIdByCompetitionId(submitDTO.getCompetitionId());

        if (divisionId != null) {
            // 创建新的竞赛提交对象
            CompetitionSubmissions submission = new CompetitionSubmissions();
            submission.setCompetitionId(submitDTO.getCompetitionId());
            submission.setDivisionId(divisionId);
            submission.setAuthorId(submitDTO.getUserId());
            submission.setInitialScore(0); // 初始分数
            submission.setInitialEvaluation("");
            submission.setSystemScore(0);
            submission.setSystemEvaluation("");
            submission.setAverageFinalScore(0.0);
            submission.setCreated_time("2024-08-25 14:00:00"); // 示例时间
            submission.setInitialRank(0);
            submission.setName("示例名称");

            // 插入竞赛提交记录
            int submissionResult = competitionSubmissionMapper.insert(submission);

            if (submissionResult > 0) {
                // 插入其他相关记录（如果有）
                // 例如：插入图片记录
                 for (String imageUrl : submitDTO.getContent()) {
                     CsubmissionImage csubmissionImage = new CsubmissionImage();
                     csubmissionImage.setSubmissionId(submission.getId());
                     csubmissionImage.setPictureUrl(imageUrl);
                     csubmissionImageMapper.insert(csubmissionImage);
                 }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

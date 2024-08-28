package com.moyunzhijiao.system_app.controller.dto.fonted.competition;

import com.moyunzhijiao.system_app.controller.dto.fonted.SubmitWritingInfo;
import lombok.Data;

import java.util.List;

@Data
public class CompetitionContentInfo {
    int id;  // 展示竞赛ID
    double score;  // 得分
    int rank;  // 排名
    String awards;  // 奖项
    String comment;  // 评语

    List<SubmitWritingInfo> submitWriting;  // 提交的图

    public CompetitionContentInfo(int id, double score, int rank, String awards, String comment, List<SubmitWritingInfo> submitWriting) {
        this.id = id;
        this.score = score;
        this.rank = rank;
        this.awards = awards;
        this.comment = comment;
        this.submitWriting = submitWriting;
    }

    // Getters and Setters
}

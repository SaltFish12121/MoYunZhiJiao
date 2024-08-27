package com.moyunzhijiao.system_app.controller.dto.fonted.competition;

import com.moyunzhijiao.system_app.controller.dto.fonted.SubmitWritingInfo;
import lombok.Data;

import java.util.List;

@Data
public class CompetitionDetailInfo {
    int id;
    String title;
    String startTime;
    String finishTime;
    String typeface;
    String require;
    Object example;
    List<SubmitWritingInfo> submit;
    int score;
    int rank;
    String award;
    String comment;
    boolean ifCollect;

    public CompetitionDetailInfo(int id, String title, String startTime, String finishTime,
                                 String typeface, String require, Object example, List<SubmitWritingInfo> submit,
                                 int score, int rank, String award, String comment, boolean ifCollect) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.typeface = typeface;
        this.require = require;
        this.example = example;
        this.submit = submit;
        this.score = score;
        this.rank = rank;
        this.award = award;
        this.comment = comment;
        this.ifCollect = ifCollect;
    }
}



package com.moyunzhijiao.system_app.controller.dto.fonted.competition;


import lombok.Data;

@Data
public class CompetitionInfo {
    int id;  // 竞赛ID
    String ifSignUp; // 类型(是否报名)
    String type; // 类型（当前类型）
    String title; // 标题
    String startTime; // 答题开始时间
    String finishTime; // 答题截止时间
    int score; // 得分

    // 构造函数
    public CompetitionInfo(int id, String ifSignUp, String type, String title, String startTime, String finishTime, int score) {
        this.id = id;
        this.ifSignUp = ifSignUp;
        this.type = type;
        this.title = title != null ? title : "";
        this.startTime = startTime != null ? startTime : "";
        this.finishTime = finishTime != null ? finishTime : "";
        this.score = score;
    }

}

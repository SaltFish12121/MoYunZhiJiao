package com.moyunzhijiao.system_app.controller.dto.fonted.exercise;

import lombok.Data;

@Data
public class ExerciseInfo {
    int id; // 作业ID
    String type; // 类型
    boolean cutoff; // 是否截止
    String title; // 标题
    String time; // 创建时间
    String cutoffTime; // 截止时间
    int score; // 得分
    boolean finish; // 是否完成

    // 构造函数
    public ExerciseInfo(int id, String type, boolean cutoff, String title, String time, String cutoffTime, int score, boolean finish) {
        this.id = id;
        this.type = type;
        this.cutoff = cutoff;
        this.title = title;
        this.time = time;
        this.cutoffTime = cutoffTime;
        this.score = score;
        this.finish = finish;
    }
}

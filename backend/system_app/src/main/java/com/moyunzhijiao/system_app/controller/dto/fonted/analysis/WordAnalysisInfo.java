package com.moyunzhijiao.system_app.controller.dto.fonted.analysis;

import lombok.Data;

@Data
public class WordAnalysisInfo {
    int id; // 所练习字的ID
    double averageScore; // 平均得分
    int[] exerciseNumber; // 练习次数
    String suggest; // 评价建议
    String template; // 模板字
    double[] score; // 得分
    ExerciseWordRecordInfo[] record; // 练习记录

    public WordAnalysisInfo(int id, double averageScore, int[] exerciseNumber, String suggest, String template, double[] score, ExerciseWordRecordInfo[] record) {
        this.id = id;
        this.averageScore = averageScore;
        this.exerciseNumber = exerciseNumber;
        this.suggest = suggest;
        this.template = template;
        this.score = score;
        this.record = record;
    }
}


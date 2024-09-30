package com.moyunzhijiao.system_app.controller.dto.fonted.analysis;


import lombok.Data;

@Data
public class WordAnalysisListInfo {
    int id; // 所练习字的ID
    double averageScore; // 平均得分
    int exerciseNumber; // 练习总次数
    String template; // 模板字

    public WordAnalysisListInfo(int id, double averageScore, int exerciseNumber, String template) {
        this.id = id;
        this.averageScore = averageScore;
        this.exerciseNumber = exerciseNumber;
        this.template = template;
    }
}


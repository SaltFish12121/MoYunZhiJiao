package com.moyunzhijiao.system_app.controller.dto.fonted.analysis;

import lombok.Data;

@Data
public class ExerciseAnalysisInfo {
    int id; // 练习ID
    Boolean type; // 练习类型（false：学校练习，true：自我练习）
    String name; // 练习名称
    String time; // 练习时间
    int score; // 练习得分

    public ExerciseAnalysisInfo(int id, Boolean type, String name, String time, int score) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.time = time;
        this.score = score;
    }
}

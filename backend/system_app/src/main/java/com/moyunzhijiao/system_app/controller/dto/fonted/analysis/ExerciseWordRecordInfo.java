package com.moyunzhijiao.system_app.controller.dto.fonted.analysis;

import com.moyunzhijiao.system_app.controller.dto.fonted.WordInfo;
import lombok.Data;

@Data
public class ExerciseWordRecordInfo {
    int id; // 练习记录的ID
    String title; // 练习标题
    String time; // 练字时间
    double score; // 练字得分
    WordInfo word; // 练习的字

    public ExerciseWordRecordInfo(int id, String title, String time, double score, WordInfo word) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.score = score;
        this.word = word;
    }
}

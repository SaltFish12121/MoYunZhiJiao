package com.moyunzhijiao.system_app.controller.dto.fonted.exercise;

import lombok.Data;

@Data
public class SchoolUnfinishExerciseDetailInfo {
    Integer id; //练习ID
    String title; //标题
    String exerciseType;  //作业类型（个人/集体）
    Boolean ifCollect; //是否收藏

    Integer difficulty;  //难度
    Integer wordNumber;  //字数
    String typeface;  //字体
    String type;  //类型
    String cutoffTime;  //截止时间

    String require; //要求
    String example;  //样例
    String[] submit; //提交的图

    Boolean cutoff;

    public SchoolUnfinishExerciseDetailInfo(Integer id, String title, String exerciseType, Boolean ifCollect,
                                            Integer difficulty, Integer wordNumber, String typeface, String type, String cutoffTime,
                                            String require, String example, String[] submit, Boolean cutoff) {
        this.id = id;
        this.title = title;
        this.exerciseType = exerciseType;
        this.ifCollect = ifCollect;
        this.difficulty = difficulty;
        this.wordNumber = wordNumber;
        this.typeface = typeface;
        this.type = type;
        this.cutoffTime = cutoffTime;
        this.require = require;
        this.example = example;
        this.submit = submit;
        this.cutoff = cutoff;
    }
}

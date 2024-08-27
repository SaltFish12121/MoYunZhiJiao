package com.moyunzhijiao.system_app.controller.dto.fonted.exercise;

import com.moyunzhijiao.system_app.controller.dto.fonted.SubmitWritingInfo;
import lombok.Data;

import java.util.List;

@Data
public class SchoolFinishExerciseDetailInfo {
    Integer id; //练习ID
    String title; //标题
    String exerciseType;  //作业类型（个人/集体）
    Boolean ifCollect; //是否收藏

    Integer difficulty;  //难度
    Integer wordNumber;  //字数
    String typeface;  //字体
    String type;  //类型

    String require; //要求
    String example;  //样例
    List<SubmitWritingInfo> submit; //提交的图

    Integer score; //得分
    String systemComment; //系统评语
    String teacherComment; //教师评语

    public SchoolFinishExerciseDetailInfo(Integer id, String title, String exerciseType, Boolean ifCollect,
                                          Integer difficulty, Integer wordNumber, String typeface, String type,
                                          String require, String example, List<SubmitWritingInfo> submit,
                                          Integer score, String systemComment, String teacherComment) {
        this.id = id;
        this.title = title;
        this.exerciseType = exerciseType;
        this.ifCollect = ifCollect;
        this.difficulty = difficulty;
        this.wordNumber = wordNumber;
        this.typeface = typeface;
        this.type = type;
        this.require = require;
        this.example = example;
        this.submit = submit;
        this.score = score;
        this.systemComment = systemComment;
        this.teacherComment = teacherComment;
    }

    // You can add a constructor with default values for systemComment and teacherComment if needed
}

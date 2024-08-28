package com.moyunzhijiao.system_app.controller.dto.fonted.excellent;

import com.moyunzhijiao.system_app.controller.dto.fonted.SubmitWritingInfo;
import lombok.Data;

import java.util.List;

@Data
public class ExcellentHomeworkInfo {
    int id;  // 收藏ID
    String title;  // 标题
    String comeFrom;  // 来源
    boolean ifCollect;  // 是否收藏

    int difficulty;  // 难度
    int wordNumber;  // 字数
    String typeface;  // 字体
    String type;  // 类型

    String require;  // 要求
    Object example;  // 样例
    List<SubmitWritingInfo> submit;  // 提交的图

    int score;  // 得分
    String systemComment;  // 系统评语
    String teacherComment;  // 教师评语

    public ExcellentHomeworkInfo(int id, String title, String comeFrom, boolean ifCollect,
                                 int difficulty, int wordNumber, String typeface, String type,
                                 String require, Object example, List<SubmitWritingInfo> submit,
                                 int score, String systemComment, String teacherComment) {
        this.id = id;
        this.title = title;
        this.comeFrom = comeFrom;
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

    // Getters and Setters
}


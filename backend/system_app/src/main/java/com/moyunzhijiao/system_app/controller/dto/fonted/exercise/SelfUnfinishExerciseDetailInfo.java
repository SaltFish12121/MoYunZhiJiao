package com.moyunzhijiao.system_app.controller.dto.fonted.exercise;

import lombok.Data;

import java.util.List;

@Data
public class SelfUnfinishExerciseDetailInfo {
    int id;  // 练习ID
    String title;  // 标题
    boolean ifCollect;  // 是否收藏
    String typeface;  // 字体
    String require;  // 要求
    String example;  // 样例
    List<String> submit;  // 提交的图

    public SelfUnfinishExerciseDetailInfo(int id, String title, boolean ifCollect,
                                          String typeface, String require, String example,
                                          List<String> submit) {
        this.id = id;
        this.title = title;
        this.ifCollect = ifCollect;
        this.typeface = typeface;
        this.require = require;
        this.example = example;
        this.submit = submit;
    }
}

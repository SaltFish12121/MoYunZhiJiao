package com.moyunzhijiao.system_app.controller.dto.fonted;

import lombok.Data;

import java.util.List;

@Data
public class WordInfo {
    public String word; // 拆出的字的图
    public List<String> strokes; // 字拆出的笔画
    public int strokeNumber; // 字得分
    public String templateWord; // 样本字
    public String comment; // 字的评语

    public WordInfo(String word, List<String> strokes, int strokeNumber, String templateWord, String comment) {
        this.word = word;
        this.strokes = strokes;
        this.strokeNumber = strokeNumber;
        this.templateWord = templateWord;
        this.comment = comment;
    }

}

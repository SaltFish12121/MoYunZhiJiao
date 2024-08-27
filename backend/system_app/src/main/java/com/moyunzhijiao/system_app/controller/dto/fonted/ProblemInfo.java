package com.moyunzhijiao.system_app.controller.dto.fonted;

import lombok.Data;

@Data
public class ProblemInfo {
    int id;  // 问题编号ID
    String type;  // 类型
    String title;  // 标题
    String question;  // 问题
    String content;  // 内容

    // 构造函数
    public ProblemInfo(int id, String type, String title, String question, String content) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.question = question;
        this.content = content;
    }

}

package com.moyunzhijiao.system_app.controller.dto.fonted;

import lombok.Data;

@Data
public class WordListInfo {
    Integer id;
    String template;    //模板字
    String name;

    // 构造函数
    public WordListInfo(Integer id, String template, String name) {
        this.id = id;
        this.template = template;
        this.name = name;
    }
}

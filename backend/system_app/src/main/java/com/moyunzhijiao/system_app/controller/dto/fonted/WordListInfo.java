package com.moyunzhijiao.system_app.controller.dto.fonted;

import lombok.Data;

@Data
public class WordListInfo {
    Integer id;
    String template;    //模板字

    // 构造函数
    public WordListInfo(Integer id, String template) {
        this.id = id;
        this.template = template;
    }
}

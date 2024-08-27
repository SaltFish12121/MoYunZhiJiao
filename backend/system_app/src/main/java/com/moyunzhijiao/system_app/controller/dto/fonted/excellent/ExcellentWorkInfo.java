package com.moyunzhijiao.system_app.controller.dto.fonted.excellent;

import lombok.Data;

@Data
public class ExcellentWorkInfo {
    int id; // 优秀作品ID
    String type; // 优秀作品类型
    String come; // 来源
    String title; // 标题
    String time; // 创建时间

    // 构造函数
    public ExcellentWorkInfo(int id, String type, String come, String title, String time) {
        this.id = id;
        this.type = type;
        this.come = come;
        this.title = title;

    }

}
package com.moyunzhijiao.system_app.controller.dto.fonted.competition;

import lombok.Data;

@Data
public class CompetitionInformInfo {
    int id;  // 竞赛ID
    boolean type;  // 竞赛类型（本/其他）
    String title;  // 竞赛标题
    String time;  // 创建时间
    String cover;  // 封面
    String content;  // 内容

    // 构造函数
    public CompetitionInformInfo(int id, boolean type, String title, String time, String cover, String content) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.time = time;
        this.cover = cover;
        this.content = content;
    }

}


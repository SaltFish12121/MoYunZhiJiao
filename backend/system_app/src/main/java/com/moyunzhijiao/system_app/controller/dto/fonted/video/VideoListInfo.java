package com.moyunzhijiao.system_app.controller.dto.fonted.video;

import lombok.Data;

@Data
public class VideoListInfo {
    Integer id;             // 视频ID
    String type;            // 视频类型（是否合集）
    String title;           // 标题
    String time;            // 创建时间
    String cover;           // 封面

    // 构造函数
    public VideoListInfo(Integer id, String type, String title, String time, String cover) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.time = time;
        this.cover = cover;
    }
}

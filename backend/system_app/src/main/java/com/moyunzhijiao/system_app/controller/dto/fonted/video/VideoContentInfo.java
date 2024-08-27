package com.moyunzhijiao.system_app.controller.dto.fonted.video;

import lombok.Data;

@Data
public class VideoContentInfo {
    int id; // 视频ID
    String coverUrl; // 视频封面URL
    String contentUrl; // 视频内容URL

    // 构造函数
    public VideoContentInfo(int id, String coverUrl, String contentUrl) {
        this.id = id;
        this.coverUrl = coverUrl;
        this.contentUrl = contentUrl;
    }
}

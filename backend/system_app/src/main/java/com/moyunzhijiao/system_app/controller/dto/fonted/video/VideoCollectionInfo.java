package com.moyunzhijiao.system_app.controller.dto.fonted.video;

import lombok.Data;

import java.util.List;

@Data
public class VideoCollectionInfo {
    int id; // 视频合集ID
    List<LabelInfo> label; // 标签
    String intro; // 简介
    List<VideoInfo> sonVideo; // 子视频
    Boolean ifCollect;

    // 构造函数
    public VideoCollectionInfo(int id, List<LabelInfo> label, String intro, List<VideoInfo> sonVideo, Boolean ifCollect) {
        this.id = id;
        this.label = label;
        this.intro = intro;
        this.sonVideo = sonVideo;
        this.ifCollect = ifCollect;
    }
}

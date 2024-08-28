package com.moyunzhijiao.system_app.controller.dto.fonted.video;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VideoInfo {
    int id; // 视频ID
    String title; // 标题
    String time; // 创建时间
    List<LabelInfo> label; // 标签
    String intro; // 简介
    String playTime; // 播放时长
    int playAmount; // 播放量
    Boolean ifCollect;

    // 构造函数
    public VideoInfo(int id, String title, String time, List<LabelInfo> label, String intro, String playTime, int playAmount, Boolean ifCollect) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.label = label != null ? label : new ArrayList<>();
        this.intro = intro != null ? intro : "";
        this.playTime = playTime != null ? playTime : "";
        this.playAmount = playAmount;
        this.ifCollect = ifCollect;
    }
}


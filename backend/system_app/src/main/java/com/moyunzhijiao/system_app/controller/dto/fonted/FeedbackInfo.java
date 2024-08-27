package com.moyunzhijiao.system_app.controller.dto.fonted;

import lombok.Data;

@Data
public class FeedbackInfo {
    Integer id;             // 反馈ID编号
    String type;            // 类型
    String content;         // 反馈内容
    String time;            // 反馈时间
    String feedback;        // 反馈回复


    public FeedbackInfo(Integer id, String type, String content, String time, String feedback) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.time = time;
        this.feedback = feedback;
    }
}


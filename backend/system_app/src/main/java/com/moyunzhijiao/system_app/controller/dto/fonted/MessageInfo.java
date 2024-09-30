package com.moyunzhijiao.system_app.controller.dto.fonted;

import lombok.Data;

@Data
public class MessageInfo {
    int id;  // 消息ID
    String type;  // 消息类型
    String title;  // 标题
    String time;  // 时间
    String content;  // 内容

    public MessageInfo(int id, String type, String title, String time, String content) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.time = time;
        this.content = content;
    }
}

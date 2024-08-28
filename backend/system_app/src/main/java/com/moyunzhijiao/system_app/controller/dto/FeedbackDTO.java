package com.moyunzhijiao.system_app.controller.dto;

import lombok.Data;

@Data
public class FeedbackDTO {
    Integer userId;
    String content;
    String type;
}

package com.moyunzhijiao.system_app.controller.dto;

import lombok.Data;

@Data
public class UploadDTO {
    Integer userId;
    Integer exerciseId;
    String[] content;
}

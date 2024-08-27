package com.moyunzhijiao.system_app.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateDTO {
    Integer userId;
    String title;
    String typeface;
    String content;
}

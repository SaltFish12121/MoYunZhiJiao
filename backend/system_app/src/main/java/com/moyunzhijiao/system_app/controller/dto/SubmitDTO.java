package com.moyunzhijiao.system_app.controller.dto;

import lombok.Data;

@Data
public class SubmitDTO {
    Integer userId;
    Integer competitionId;
    String[] content;
}

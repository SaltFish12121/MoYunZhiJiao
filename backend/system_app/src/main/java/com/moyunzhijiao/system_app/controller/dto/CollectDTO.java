package com.moyunzhijiao.system_app.controller.dto;

import lombok.Data;

@Data
public class CollectDTO {
    Integer userId;
    String collectType;
    Integer collectId;
    Boolean newCollect;
}

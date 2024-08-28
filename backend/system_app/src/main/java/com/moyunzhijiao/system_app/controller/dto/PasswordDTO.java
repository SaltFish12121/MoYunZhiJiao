package com.moyunzhijiao.system_app.controller.dto;

import lombok.Data;

@Data
public class PasswordDTO {
    Integer userId;
    String oldPassword;
    String newPassword;
}

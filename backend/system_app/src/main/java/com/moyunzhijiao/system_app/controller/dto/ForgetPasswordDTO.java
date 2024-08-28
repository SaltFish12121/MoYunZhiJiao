package com.moyunzhijiao.system_app.controller.dto;

import lombok.Data;

@Data
public class ForgetPasswordDTO {
    private String phoneNumber;
    private String newPassword;
}

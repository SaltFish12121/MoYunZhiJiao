package com.moyunzhijiao.system_app.controller.dto;

import lombok.Data;

@Data
public class UserDTO {
    Integer id;
    String name;
    String studentNumber;       //学籍号
    String password;

    Integer klassId;
    String klass;
    String school;
    Integer gradeId;
    String grade;

    String token;           //token
    Integer schoolId;
    String phone;
    String email;

    Integer regionId;
    String region;

    String idNumber;        //身份证号
    String gender;
    String pictureUrl;
}

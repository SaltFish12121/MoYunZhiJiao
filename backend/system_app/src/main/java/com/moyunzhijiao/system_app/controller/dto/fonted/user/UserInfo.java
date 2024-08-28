package com.moyunzhijiao.system_app.controller.dto.fonted.user;

import lombok.Data;

@Data
public class UserInfo {
    Integer id;  // 用户id
    String name;  // 用户名
    String studentNumber; // 学号
    String userPassword;  // 密码

    RegionInfo region;   // 地区
    String gender;  // 性别
    String otherName;   // 头像

    KlassInfo klass;  // 班级

    String phoneNumber; // 手机号
    String email; // 电子邮箱
    String idNumber;  // 身份证号

    Boolean ifBinding; // 是否绑定学校
}

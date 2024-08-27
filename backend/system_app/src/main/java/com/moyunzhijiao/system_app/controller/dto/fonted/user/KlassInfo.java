package com.moyunzhijiao.system_app.controller.dto.fonted.user;

import lombok.Data;

@Data
public class KlassInfo {
    Integer id;  // 班级id
    String name;  // 班级名
    String school;  // 学校
    String grade;  // 年级
    String teacher;  // 教师

    // 添加构造函数
    public KlassInfo(Integer id, String name, String school, String grade, String teacher) {
        this.id = id;
        this.name = name;
        this.school = school;
        this.grade = grade;
        this.teacher = teacher;
    }
}

package com.moyunzhijiao.system_app.entity.exercise;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student_create_homework")
public class StudentHomework {
    @TableId("student_id")
    Integer studentId;
    @TableField("homework_id")
    Integer homeworkId;
    @TableField("template_id")
    Integer templateId;
    @TableField("template_type")
    String templateType;
}

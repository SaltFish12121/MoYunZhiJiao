package com.moyunzhijiao.system_app.entity.exercise;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("homework_submission")
public class HomeworkSubmission {
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    @TableField("system_score")
    Integer systemScore;
    @TableField("system_feedback")
    String systemFeedback;
    @TableField("teacher_score")
    Integer teacherScore;
    @TableField("teacher_feedback")
    String teacherFeedback;
    @TableField("submited_time")
    String submitedTime;
    @TableField("homework_id")
    Integer homeworkId;
    @TableField("student_id")
    Integer studentId;
    Integer state;
    Integer reviewed;
}

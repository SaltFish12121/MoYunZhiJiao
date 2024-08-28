package com.moyunzhijiao.system_app.entity.collection;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="stu_submission_collection")
public class ExerciseCollection {
    @TableId(value = "student_id")
    Integer id;
    @TableField("submission_id")
    Integer submissionId;
    @TableField("type")
    String type;
}


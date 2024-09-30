package com.moyunzhijiao.system_app.entity.exercise;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("character_analysis")
public class CharacterAnalysis {
    @TableId(type = IdType.AUTO )
    Integer id;
    @TableField("submission_id")
    Integer submissionId;
    Integer score;
    String evaluation;
    String picture;
    @TableField("created_time")
    String createdTime;
    String name;
    @TableField("student_id")
    String studentId;
}

package com.moyunzhijiao.system_app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("feedback_content")
public class FeedbackContent {
    @TableId("feedback_id")
    Integer feedbackId;
    String message;
    String reply;
    String file;
}

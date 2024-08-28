package com.moyunzhijiao.system_app.entity.exercise;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("hsubmission_image")
public class HsubmissionImage {
    @TableId("submission_id")
    Integer submissionId;
    @TableField("picture_url")
    String pictureUrl;
}

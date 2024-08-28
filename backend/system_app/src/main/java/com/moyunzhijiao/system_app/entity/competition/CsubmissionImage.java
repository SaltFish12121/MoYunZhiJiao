package com.moyunzhijiao.system_app.entity.competition;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("csubmission_image")
public class CsubmissionImage {
    @TableId("submission_id")
    Integer submissionId;
    @TableField("picture_url")
    String pictureUrl;
}

package com.moyunzhijiao.system_app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("system_template_image")
public class SystemTemplateImage {
    @TableId("system_template_id")
    Integer systemTemplateId;
    @TableField("picture_url")
    String pictureUrl;
}

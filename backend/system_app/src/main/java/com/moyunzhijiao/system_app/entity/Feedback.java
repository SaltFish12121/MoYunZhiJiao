package com.moyunzhijiao.system_app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("feedback")
public class Feedback {
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    @TableField("provider_id")
    Integer providerId;
    @TableField("provider_type")
    String providerType;
    String type;
    String state;
    String provider_phone;
    @TableField("created_time")
    String createdTime;
    String editor;
}

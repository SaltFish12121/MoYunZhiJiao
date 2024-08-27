package com.moyunzhijiao.system_app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("system_template")
public class SystemTemplate {
    @TableId
    Integer id;
    String name;
    String type;
    @TableField("font_id")
    Integer fontId;
    Integer creator_id;
    String difficulty;
    Integer word_count;
    @TableField("created_time")
    String createdTime;
}

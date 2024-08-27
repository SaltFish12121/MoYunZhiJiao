package com.moyunzhijiao.system_app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("video")
public class Video {
    @TableId("id")
    Integer id;
    String name;
    Integer first_type_id;
    Integer second_type_id;
    String tag;
    @TableField("is_recommended")
    Integer isRecommended;
    String content;
    @TableField("picture_url")
    String pictureUrl;
    @TableField("created_time")
    String createdTime;
    String summary;
    String importer;
}

package com.moyunzhijiao.system_app.entity.competition;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("announcement")
public class Announcement {
    @TableId
    Integer id;
    String name;
    String target;
    String state;
    String type;
    @TableField("created_time")
    String createdTime;
    @TableField("picture_url")
    String pictureUrl;
    @TableField("release_time")
    String releaseTime;
    String publisher;
}

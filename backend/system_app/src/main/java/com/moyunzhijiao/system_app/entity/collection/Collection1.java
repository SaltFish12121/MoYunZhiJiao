package com.moyunzhijiao.system_app.entity.collection;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("collection")
public class Collection1 {
    //合集
    @TableId("id")
    Integer id;
    String name;
    Integer first_type_id;
    Integer second_type_id;
    String tag;
    @TableField("picture_url")
    String pictureUrl;
    String summary;
    @TableField("created_time")
    String createdTime;
    @TableField("is_recommended")
    Integer isRecommended;
    String importer;
}

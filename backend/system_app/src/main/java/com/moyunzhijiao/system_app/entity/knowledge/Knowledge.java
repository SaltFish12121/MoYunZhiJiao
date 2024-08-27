package com.moyunzhijiao.system_app.entity.knowledge;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("article")
public class Knowledge {
    @TableId
    Integer id;
    String name;
    Integer first_type_id;
    Integer second_type_id;
    String tag;
    @TableField("is_recommended")
    Integer isRecommended;
    @TableField("picture_url")
    String pictureUrl;
    @TableField("created_time")
    String createdTime;
    String importer;
}

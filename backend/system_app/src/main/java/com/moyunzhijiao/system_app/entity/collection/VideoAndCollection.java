package com.moyunzhijiao.system_app.entity.collection;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("video_collection")
public class VideoAndCollection {
    @TableId("video_id")
    Integer videoId;
    @TableField("collection_id")
    Integer collectionId;
    Integer sequence;
}

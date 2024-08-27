package com.moyunzhijiao.system_app.entity.collection;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("video_knowledge")
public class VideoCollection {
    //视频与合集
    @TableId("student_id")
    Integer studentId;
    @TableField("video_id")
    Integer videoId;
}

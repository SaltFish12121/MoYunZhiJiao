package com.moyunzhijiao.system_app.entity.competition;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("announcement_content")
public class AnnouncementContent {
    @TableId("announcement_id")
    Integer announcementId;
    String message;
}

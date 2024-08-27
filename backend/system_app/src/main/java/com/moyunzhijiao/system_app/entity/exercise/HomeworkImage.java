package com.moyunzhijiao.system_app.entity.exercise;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("homework_image")
public class HomeworkImage {
    @TableId("homework_id")
    Integer homeworkId;
    @TableField("picture_url")
    String pictureUrl;
}

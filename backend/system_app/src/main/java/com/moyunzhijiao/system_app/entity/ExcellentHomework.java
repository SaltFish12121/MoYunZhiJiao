package com.moyunzhijiao.system_app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("outstanding_homework")
public class ExcellentHomework {
    @TableId("submissions_id")
    Integer submissionsId;
    @TableField("recommender_id")
    Integer recommenderId;
    Integer reviewer_id;
    String updated_time;
}

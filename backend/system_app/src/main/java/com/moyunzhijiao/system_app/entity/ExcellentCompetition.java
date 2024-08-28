package com.moyunzhijiao.system_app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("outstanding_competition")
public class ExcellentCompetition {
    @TableId("submissions_id")
    Integer submissionsId;
    Integer created_time;
}

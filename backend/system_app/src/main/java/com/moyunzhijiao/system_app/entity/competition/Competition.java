package com.moyunzhijiao.system_app.entity.competition;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="competition")
public class Competition {
    @TableId(value="id",type = IdType.AUTO)
    Integer id;
    @TableField("name")
    String name;
    @TableField("registration_start_time")
    String registrationStartTime;
    @TableField("registration_end_time")
    String registrationEndTime;
    @TableField("competition_start_time")
    String competitionStartTime;
    @TableField("competition_end_time")
    String competitionEndTime;
    @TableField("review_start_time")
    String review_start_time;
//    @TableField("review_end_time")
//    String review_end_time;
    @TableField("state")
    String state;
    @TableField("created_time")
    String createdTime;
    Integer count;
    String picture;
}

package com.moyunzhijiao.system_app.entity.competition;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("division")
public class Division {
    @TableId
    Integer id;
    String name;
    @TableField("competition_id")
    Integer competitionId;
    Integer special_prize_ratio;
    Integer first_prize_ratio;
    Integer second_prize_ratio;
    Integer third_prize_ratio;
    String target;
    String created_time;
    Integer delete_flag;
}

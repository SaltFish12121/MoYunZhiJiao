package com.moyunzhijiao.system_app.entity.competition;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("competition_requirements")
public class CompetitionRequirements {
    @TableId("competition_id")
    Integer competitionId;
    String requirements;
}

package com.moyunzhijiao.system_app.entity.competition;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("division_requirements")
public class DivisionRequirements {
    @TableId("division_id")
    Integer divisionId;
    String requirements;
}

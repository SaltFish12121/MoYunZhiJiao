package com.moyunzhijiao.system_app.entity.competition;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("participant")
public class Participant {
    @TableId("division_id")
    Integer divisionId;
    @TableField("student_id")
    Integer studentId;
    @TableField("submission_id")
    Integer submissionId;
    @TableField("competition_id")
    Integer competitionId;
}

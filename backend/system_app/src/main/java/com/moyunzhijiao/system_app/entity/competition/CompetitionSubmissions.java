package com.moyunzhijiao.system_app.entity.competition;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("competition_submissions")
public class CompetitionSubmissions {
    Integer id;
    @TableField("competition_id")
    Integer competitionId;
    @TableField("division_id")
    Integer divisionId;
    @TableField("author_id")
    Integer authorId;
    @TableField("initial_score")
    Integer initialScore;
    @TableField("initial_evaluation")
    String initialEvaluation;
    @TableField("system_score")
    Integer systemScore;
    @TableField("system_evaluation")
    String systemEvaluation;
    @TableField("average_final_score")
    Double averageFinalScore;
    String created_time;
    @TableField("initial_rank")
    Integer initialRank;
    String name;
}

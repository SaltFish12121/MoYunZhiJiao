package com.moyunzhijiao.system_app.entity.exercise;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("stroke_analysis")
public class StrokeAnalysis {
    @TableId
    Integer id;
    @TableField("character_analysis_id")
    Integer characterAnalysisId;
    Integer score;
    String picture;
    String created_time;
}


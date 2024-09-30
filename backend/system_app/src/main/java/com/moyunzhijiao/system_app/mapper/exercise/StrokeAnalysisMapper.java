package com.moyunzhijiao.system_app.mapper.exercise;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_app.entity.exercise.StrokeAnalysis;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StrokeAnalysisMapper extends BaseMapper<StrokeAnalysis> {
    @Select("SELECT * FROM stroke_analysis WHERE character_analysis_id = #{characterAnalysisId}")
    List<StrokeAnalysis> selectByCharacterAnalysisId(@Param("characterAnalysisId") Integer characterAnalysisId);
}

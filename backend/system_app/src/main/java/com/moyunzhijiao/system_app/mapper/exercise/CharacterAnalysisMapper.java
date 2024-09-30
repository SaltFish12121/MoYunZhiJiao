package com.moyunzhijiao.system_app.mapper.exercise;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_app.entity.exercise.CharacterAnalysis;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CharacterAnalysisMapper extends BaseMapper<CharacterAnalysis> {
    @Select("SELECT * FROM character_analysis WHERE student_id = #{userId}")
    List<CharacterAnalysis> selectByUserId(@Param("userId") Integer userId);

    @Select("SELECT * FROM character_analysis WHERE student_id = #{userId} AND name = #{wordName}")
    List<CharacterAnalysis> selectByUserIdAndWordName(@Param("userId") Integer userId, @Param("wordName") String wordName);
}


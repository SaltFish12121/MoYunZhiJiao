package com.moyunzhijiao.system_app.mapper.competition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_app.entity.competition.CompetitionSubmissions;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CompetitionSubmissionMapper extends BaseMapper<CompetitionSubmissions> {
    @Select("SELECT * FROM competition_submissions WHERE author_id = #{userId} AND competition_id = #{competitionId}")
    CompetitionSubmissions findByUserIdAndCompetitionId(@Param("userId") Integer userId, @Param("competitionId") Integer competitionId);
}

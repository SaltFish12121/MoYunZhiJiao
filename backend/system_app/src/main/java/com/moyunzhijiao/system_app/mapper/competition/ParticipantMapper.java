package com.moyunzhijiao.system_app.mapper.competition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_app.entity.competition.Participant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ParticipantMapper extends BaseMapper<Participant> {
    @Select("SELECT COUNT(*) > 0 FROM participant WHERE student_id = #{userId} AND competition_id = #{competitionId}")
    boolean existsByStudentIdAndCompetitionId(@Param("userId") Integer userId, @Param("competitionId") Integer competitionId);
}

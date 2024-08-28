package com.moyunzhijiao.system_app.mapper.competition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_app.entity.competition.Division;
import org.apache.ibatis.annotations.Select;

public interface DivisionMapper extends BaseMapper<Division> {
    @Select("SELECT id FROM Division WHERE competition_id = #{competitionId}")
    Integer findDivisionIdByCompetitionId(Integer competitionId);
}

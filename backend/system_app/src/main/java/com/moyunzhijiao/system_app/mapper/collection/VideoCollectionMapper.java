package com.moyunzhijiao.system_app.mapper.collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_app.entity.collection.VideoCollection;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface VideoCollectionMapper extends BaseMapper<VideoCollection> {
    @Select("SELECT COUNT(*) > 0 FROM video_knowledge WHERE student_id = #{userId} AND video_id = #{videoId}")
    Boolean existsByUserIdAndVideoId(@Param("userId") Integer userId, @Param("videoId") Integer videoId);
}

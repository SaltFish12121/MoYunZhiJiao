package com.moyunzhijiao.system_app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_app.entity.SystemTemplateImage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SystemTemplateImageMapper extends BaseMapper<SystemTemplateImage> {
    @Select("SELECT * FROM system_template_image WHERE system_template_id = #{templateId} LIMIT 1")
    SystemTemplateImage selectFirstByTemplateId(@Param("templateId") int templateId);
}

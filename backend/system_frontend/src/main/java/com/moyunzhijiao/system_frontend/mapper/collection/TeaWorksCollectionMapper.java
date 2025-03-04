package com.moyunzhijiao.system_frontend.mapper.collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.entity.collection.TeaWorksCollection;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TeaWorksCollectionMapper extends BaseMapper<TeaWorksCollection> {

    @Select("SELECT " +
            "    twc.submission_id AS id, " +
            "    twc.submission_type as source ," +
            "    twc.created_time as created_time ," +
            "    CASE  " +
            "        WHEN twc.submission_type = '作业作品' THEN hw.name " +
            "        WHEN twc.submission_type = '优秀竞赛作品' THEN oc.name " +
            "        WHEN twc.submission_type = '优秀作业作品' THEN oh.name " +
            "    END AS name ," +
            "    CASE  " +
            "         WHEN twc.submission_type = '作业作品' THEN hw.type " +
            "         WHEN twc.submission_type = '优秀作业作品' THEN ( SELECT hw.type FROM homework hw WHERE hw.id = oh.submissions_id )" +
            "     END AS type ," +
            "    case " +
            "         when twc.submission_type = '作业作品' THEN hw.detail_type " +
            "         WHEN twc.submission_type = '优秀作业作品' THEN ( SELECT hw.detail_type FROM homework hw WHERE hw.id = oh.submissions_id )" +
            "     end as detail_type " +
            "FROM  " +
            "    tea_works_collection twc " +
            "LEFT JOIN  " +
            "    homework hw ON twc.submission_id = hw.id AND twc.submission_type = '作业作品' " +
            "LEFT JOIN  " +
            "    outstanding_competition oc ON twc.submission_id = oc.submissions_id AND twc.submission_type = '优秀竞赛作品' " +
            "LEFT JOIN  " +
            "    outstanding_homework oh ON twc.submission_id = oh.submissions_id AND twc.submission_type = '优秀作业作品' " +
            "WHERE  " +
            "    twc.teacher_id = #{teacherId}; ")
    IPage<Homework> selectHomeworkOfTeacher(IPage<Homework> page, @Param("teacherId") Integer teacherId);

    @Select("SELECT " +
            "    count(*)" +
            "FROM  " +
            "    tea_works_collection twc " +
            "WHERE  " +
            "    twc.teacher_id = #{teacherId}; ")
    Integer countHomeworkOfTeacher(@Param("teacherId") Integer teacherId);
}

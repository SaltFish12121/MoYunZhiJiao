package com.moyunzhijiao.system_app.mapper.exercise;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_app.entity.exercise.HomeworkSubmission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HomeworkSubmissionMapper extends BaseMapper<HomeworkSubmission> {
    @Select("SELECT * FROM homework_submission WHERE student_id = #{studentId} AND homework_id = #{homeworkId}")
    List<HomeworkSubmission> findByStudentIdAndHomeworkId(@Param("studentId") Integer studentId, @Param("homeworkId") Integer homeworkId);

    @Select("SELECT * FROM homework_submission WHERE student_id = #{studentId}")
    List<HomeworkSubmission> findByStudentId(@Param("studentId") Integer studentId);
}

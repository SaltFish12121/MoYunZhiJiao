package com.moyunzhijiao.system_app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_app.controller.dto.UserDTO;
import com.moyunzhijiao.system_app.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper extends BaseMapper<Student> {
    @Select("SELECT * FROM student WHERE id = #{userId}")
    UserDTO selectInformation(@Param("userId") Integer userId);

    @Update("UPDATE student SET name = #{name}, gender = #{gender}, picture_url = #{pictureUrl}, email = #{email}, phone = #{phone}, password = #{password} WHERE id = #{id}")
    void updateInformation(UserDTO userDTO);
}

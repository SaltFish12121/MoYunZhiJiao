package com.moyunzhijiao.system_app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_app.controller.dto.UserDTO;
import com.moyunzhijiao.system_app.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface LoginMapper extends BaseMapper<Student> {

    // 更新用户密码
    @Update("UPDATE student SET password = #{newPassword} WHERE phone = #{phoneNumber}")
    void updatePassword(@Param("phoneNumber") String phoneNumber, @Param("newPassword") String newPassword);

    // 检查用户名是否已存在
    @Select("SELECT COUNT(*) > 0 FROM student WHERE name = #{userName}")
    boolean existsByUserName(@Param("userName") String userName);

    // 检查手机号是否已存在
    @Select("SELECT COUNT(*) > 0 FROM student WHERE phone = #{phone}")
    boolean existsByPhone(@Param("phone") String phone);
}


package com.moyunzhijiao.system_app.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_app.controller.dto.*;
import com.moyunzhijiao.system_app.controller.dto.fonted.ReasonInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.user.KlassInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.user.RegionInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.user.UserInfo;
import com.moyunzhijiao.system_app.entity.Student;
import com.moyunzhijiao.system_app.mapper.RegionMapper;
import com.moyunzhijiao.system_app.mapper.UserMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, Student> {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RegionMapper regionMapper;

    //获取用户信息
    public UserInfo getPersonal(Integer userId) {
        // 查询用户信息
        UserDTO userDTO = userMapper.selectInformation(userId);
        userDTO.setRegion(regionMapper.selectById(userDTO.getRegionId()).getName());

        // 装配
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userDTO.getId());
        userInfo.setName(userDTO.getName());
        userInfo.setStudentNumber(userDTO.getStudentNumber());
        userInfo.setUserPassword(userDTO.getPassword());
        userInfo.setRegion(new RegionInfo(userDTO.getRegionId(), userDTO.getRegion()));
        userInfo.setGender(userDTO.getGender());
        userInfo.setOtherName(userDTO.getPictureUrl());
        userInfo.setKlass(new KlassInfo(userDTO.getKlassId(), userDTO.getKlass(), userDTO.getSchool(), userDTO.getGrade(), "TeacherName")); // 使用构造函数
        userInfo.setPhoneNumber(userDTO.getPhone());
        userInfo.setEmail(userDTO.getEmail());
        userInfo.setIdNumber(userDTO.getIdNumber());
        userInfo.setIfBinding(userDTO.getSchoolId() != null); // 根据实际情况设置


        // 返回用户信息
        return userInfo;
    }


    //获取我的消息
    public void getMineMessage(Integer userId, Integer messageType, Integer pageNumber) {

    }


    // 更改性别
    public boolean alterGender(GenderDTO genderDTO) {
        // 查询用户信息
        UserDTO userDTO = userMapper.selectInformation(genderDTO.getUserId());

        if (userDTO != null) {
            // 更新性别
            userDTO.setGender(genderDTO.getGender());
            return updateUserInfo(userDTO);
        } else {
            return false;
        }
    }


    // 更改姓名
    public boolean alterUserName(NameDTO userNameDTO) {
        // 查询用户信息
        UserDTO userDTO = userMapper.selectInformation(userNameDTO.getUserId());

        if (userDTO != null) {
            // 更新姓名
            userDTO.setName(userNameDTO.getNewName());
            return updateUserInfo(userDTO);
        } else {
            return false;
        }
    }


    // 更改头像
    public boolean alterAvatar(AvatarDTO avatarDTO) {
        // 查询用户信息
        UserDTO userDTO = userMapper.selectInformation(avatarDTO.getUserId());

        if (userDTO != null) {
            // 更新头像
            userDTO.setPictureUrl(avatarDTO.getAvatar());
            return updateUserInfo(userDTO);
        } else {
            return false;
        }
    }


    // 更改邮箱
    public boolean alterEmail(EmailDTO emailDTO) {
        // 查询用户信息
        UserDTO userDTO = userMapper.selectInformation(emailDTO.getUserId());

        if (userDTO != null) {
            // 更新邮箱
            userDTO.setEmail(emailDTO.getEmail());
            return updateUserInfo(userDTO);
        } else {
            return false;
        }
    }


    // 更改手机号
    public boolean alterPhoneNumber(PhoneDTO phoneNumberDTO) {
        // 查询用户信息
        UserDTO userDTO = userMapper.selectInformation(phoneNumberDTO.getUserId());

        if (userDTO != null) {
            // 更新手机号
            userDTO.setPhone(phoneNumberDTO.getNewPhoneNumber());
            return updateUserInfo(userDTO);
        } else {
            return false;
        }
    }


    // 更改密码
    public ReasonInfo alterPassword(PasswordDTO passwordDTO) {
        ReasonInfo reason = new ReasonInfo();

        // 查询用户信息
        UserDTO userDTO = userMapper.selectInformation(passwordDTO.getUserId());

        if (userDTO != null) {
            // 更新密码
            userDTO.setPassword(passwordDTO.getNewPassword());
            updateUserInfo(userDTO);

            reason.setSuccess(true);
            reason.setMessage("密码修改成功");
        } else {
            reason.setSuccess(false);
            reason.setMessage("用户不存在");
        }

        return reason;
    }


    // 更新用户信息
    private boolean updateUserInfo(UserDTO userDTO) {
        if (userDTO != null) {
            userMapper.updateInformation(userDTO);
            return true;
        } else {
            return false;
        }
    }
}

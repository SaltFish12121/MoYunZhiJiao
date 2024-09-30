package com.moyunzhijiao.system_app.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_app.controller.dto.*;
import com.moyunzhijiao.system_app.controller.dto.fonted.KnowledgeInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.MessageInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.ReasonInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.user.KlassInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.user.RegionInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.user.UserInfo;
import com.moyunzhijiao.system_app.entity.Student;
import com.moyunzhijiao.system_app.entity.message.Note;
import com.moyunzhijiao.system_app.entity.message.NoteContent;
import com.moyunzhijiao.system_app.entity.message.StudentNoteReceive;
import com.moyunzhijiao.system_app.mapper.message.NoteMapper1;
import com.moyunzhijiao.system_app.mapper.message.NoteContentMapper;
import com.moyunzhijiao.system_app.mapper.message.StudentNoteReceiveMapper;
import com.moyunzhijiao.system_app.mapper.RegionMapper;
import com.moyunzhijiao.system_app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, Student> {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RegionMapper regionMapper;
    @Autowired
    NoteMapper1 noteMapper;
    @Autowired
    StudentNoteReceiveMapper studentNoteReceiveMapper;
    @Autowired
    NoteContentMapper noteContentMapper;

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
    public List<MessageInfo> getMineMessage(Integer userId, Integer messageType, Integer pageNumber, Integer pageSize) {
        String type = switch (messageType) {
            case 0 -> "系统消息";
            case 1 -> "布置作业";
            case 2 -> "作业催促";
            case 3 -> "竞赛消息";
            default -> "";
        };

        // 查询学生消息关系表
        QueryWrapper<StudentNoteReceive> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId);
        List<StudentNoteReceive> studentNotes = studentNoteReceiveMapper.selectList(queryWrapper);

        List<MessageInfo> messages = new ArrayList<>();

        for (StudentNoteReceive studentNote : studentNotes) {
            // 查询消息表
            Note note = noteMapper.selectById(studentNote.getNoteId());
            // 查询消息内容表
            NoteContent noteContent = noteContentMapper.selectById(note.getId());

            // 仅添加匹配的消息类型
            if (note.getType().equals(type)) {
                // 装配消息信息
                MessageInfo message = new MessageInfo(
                        note.getId(),
                        note.getType(),
                        note.getName(),
                        note.getCreatedTime(),
                        noteContent.getMessage()
                );
                messages.add(message);
            }
        }

        // 分页处理
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, messages.size());

        // 检查索引有效性
        if (start >= messages.size()) {
            // 如果结果为空或起始索引超出范围，返回空列表
            return new ArrayList<>();
        }

        List<MessageInfo> pagedResults = messages.subList(start, end);

        return pagedResults;
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

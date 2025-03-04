package com.moyunzhijiao.system_frontend.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.controller.dto.TeacherDTO;
import com.moyunzhijiao.system_frontend.entity.Teacher;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.mapper.TeacherMapper;
import com.moyunzhijiao.system_frontend.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class TeacherService extends ServiceImpl<TeacherMapper, Teacher> {
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    RegionService regionService;
    /*
    * 获取一个教师的个人信息
    * */
    public TeacherDTO getInformation(Integer teacherId) {
        TeacherDTO teacherDTO = teacherMapper.selectInformation(teacherId);
        String grade = teacherDTO.getGrade();
        //去除年级里的六三制和五四制字词（保存在了数据库里）
        grade = StrUtil.removeAll(grade,"六三制");
        grade = StrUtil.removeAll(grade,"五四制");
        teacherDTO.setGrade(grade);
        teacherDTO.setArea(regionService.selectAllNameById(teacherDTO.getRegionId()));
        return teacherDTO;
    }

    /*
    * 更新教师的头像
    * */
    @Transactional
    public String updateAvatar(Integer teacherId, MultipartFile avatar) {
        Teacher teacher = teacherMapper.selectById(teacherId);

        //找到旧的文件名
        String oldUrl = teacher.getAvatar();
        String oldFileName = StrUtil.subAfter(oldUrl, "/", true);
        // 获取项目根目录
        String avatarFilePath = ConfigService.getAvatarFilePath();
        String oldImageFilePath = avatarFilePath + oldFileName;
        File oldImageFile = new File(oldImageFilePath);
        //尝试删除旧的文件
        if (oldImageFile.exists()) {
            oldImageFile.delete();
        }
        //更新文件
        String newOriginalFileName = avatar.getOriginalFilename();
        String newImageFileName = UUID.randomUUID()+"-"+newOriginalFileName;
        String newImageFilePath = avatarFilePath+newImageFileName;
        //传给前端的后端的API接口
        String newImageFileUrl = ConfigService.getAvatarUrl()+"/"+newImageFileName;
        File newImageDest = new File(newImageFilePath);
        try {
            //保存文件
            avatar.transferTo(newImageDest);
            //保存数据到数据库
            teacher.setAvatar(newImageFileUrl);
            teacherMapper.updateById(teacher);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500, "系统保存文件失败");
        }
        return teacher.getAvatar();
    }

    public TeacherDTO login(TeacherDTO teacherDTO) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("workno",teacherDTO.getWorkno());
        queryWrapper.eq("password",teacherDTO.getPassword());
        Teacher one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            //这里假设查询了多于1条记录，就让他报系统错误
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"参数错误，存在多个对象");
        }
        if(one!=null){
            BeanUtil.copyProperties(one,teacherDTO);
            String token = TokenUtils.checkToken(teacherDTO.getToken(),one.getId().toString(),"教师",one.getPassword());
            teacherDTO.setToken(token);
            return teacherDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    @Transactional
    public void updateInformation(TeacherDTO teacherDTO) {
        try {
            Teacher teacher = convertToEntity(teacherDTO);
            int rows = teacherMapper.updateById(teacher);
            if (rows != 1) {
                throw new ServiceException(Constants.CODE_500,"更新失败，未找到对应的教师记录");
            }
        } catch (ServiceException e) {
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    /*
    * 转化为存到数据库的实体类
    * */
    private Teacher convertToEntity(TeacherDTO teacherDTO){
        Teacher teacher = new Teacher();
        BeanUtil.copyProperties(teacherDTO,teacher);
        return teacher;
    }
    public String getNameById(Integer teacherId){
        Teacher teacher = teacherMapper.selectById(teacherId);
        return teacher.getName();
    }
}

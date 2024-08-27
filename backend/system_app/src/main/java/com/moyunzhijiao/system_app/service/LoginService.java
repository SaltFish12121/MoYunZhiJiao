package com.moyunzhijiao.system_app.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_app.common.Constants;
import com.moyunzhijiao.system_app.controller.dto.ForgetPasswordDTO;
import com.moyunzhijiao.system_app.controller.dto.LoginDTO;
import com.moyunzhijiao.system_app.controller.dto.UserDTO;
import com.moyunzhijiao.system_app.entity.Student;
import com.moyunzhijiao.system_app.exception.ServiceException;
import com.moyunzhijiao.system_app.mapper.LoginMapper;
import com.moyunzhijiao.system_app.utils.TokenUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService extends ServiceImpl<LoginMapper, Student> {
    @Autowired
    LoginMapper loginMapper;

    public UserDTO login(LoginDTO loginDTO) {
        //使用mybatis-plus查询数据库
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();

        // 判断是否传递了密码
        if (loginDTO.getUserPassword() != null) {
            queryWrapper.eq(Student::getPassword, loginDTO.getUserPassword())
                    .and(wrapper -> wrapper.eq(Student::getStudentNumber, loginDTO.getUserName())
                            .or()
                            .eq(Student::getPhone, loginDTO.getUserName()))
                            .or()
                            .eq(Student::getName, loginDTO.getUserName())
                    .last("LIMIT 1"); // 添加 LIMIT 1 以确保只返回一条记录
        } else {
            queryWrapper.eq(Student::getPhone, loginDTO.getUserName());
        }

        Student one;
        try {
            //接口里的函数，mapper里也有许多写好的数据库操作可直接studentMapper.来调用
            one = getOne(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"系统错误1");
        }
        if(one!=null){
            UserDTO userDTO = new UserDTO();
            //使用hutool工具赋值类内相同名称的变量的数据
            BeanUtil.copyProperties(one, userDTO);
            //设置token
            String token = TokenUtils.checkToken(userDTO.getToken(),one.getId().toString(),one.getPassword());
            userDTO.setToken(token);
            return userDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    @Transactional
    public void forgetPassword(ForgetPasswordDTO request) {
        // 根据手机号查询用户
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getPhone, request.getPhoneNumber());
        Student user = loginMapper.selectOne(queryWrapper);

        if (user == null) {
            throw new ServiceException(Constants.CODE_400, "用户不存在");
        }

        // 更新用户密码
        loginMapper.updatePassword(request.getPhoneNumber(), request.getNewPassword());
    }

    public String getPhoneVerify(String phoneNumber) {
        // 生成6位数验证码
        String verifyCode = String.valueOf((int)((Math.random() * 9 + 1) * 100000));

        // 配置阿里云短信服务
        /*
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "<your-access-key-id>", "<your-access-key-secret>");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "<your-sign-name>");
        request.putQueryParameter("TemplateCode", "<your-template-code>");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + verifyCode + "\"}");

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500, "发送短信失败");
        }
        */

        return verifyCode;
    }

    @Transactional
    public void register(UserDTO userDTO) {
        try {
            // 检查用户名是否已存在
            if (loginMapper.existsByUserName(userDTO.getName())) {
                throw new ServiceException(Constants.CODE_400, "用户名已存在");
            }

            // 检查手机号是否已存在
            if (loginMapper.existsByPhone(userDTO.getPhone())) {
                throw new ServiceException(Constants.CODE_400, "手机号已存在");
            }

            // 创建一个新的 Student 对象
            Student student = new Student();
            BeanUtil.copyProperties(userDTO, student);

            // 设置其他必要的属性
            student.setDeleteFlag(false);
            // student.setCreatedTime(LocalDateTime.now().toString());

            // 将学生信息插入数据库
            loginMapper.insert(student);
        } catch (Exception e) {
            // 记录异常信息
            log.error("Error inserting student: ", e);
            throw e;
        }
    }



}

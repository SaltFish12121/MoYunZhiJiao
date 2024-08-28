package com.moyunzhijiao.system_app.controller;

import cn.hutool.core.util.StrUtil;
import com.moyunzhijiao.system_app.common.Constants;
import com.moyunzhijiao.system_app.common.Result;
import com.moyunzhijiao.system_app.controller.dto.ForgetPasswordDTO;
import com.moyunzhijiao.system_app.controller.dto.LoginDTO;
import com.moyunzhijiao.system_app.controller.dto.RegisterDTO;
import com.moyunzhijiao.system_app.controller.dto.UserDTO;
import com.moyunzhijiao.system_app.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "登录接口")
@RequestMapping("/LoginService")
public class LoginController {
    @Autowired
    LoginService loginService;

    /**
     * login
     * @param loginDTO
     * @return
     */
    @Operation(summary = "用户登陆，登录注册之类的需要到配置文件中添加放行路径，在InterceptorConfig文件中，否则会被token拦截验证")
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO){
        String userName = loginDTO.getUserName();
        String userPassword = loginDTO.getUserPassword();
        //调用hutool工具中的StrUtil函数实现用户名和密码是否为空的判断
        if(StrUtil.isBlank(userName)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO userDTO = loginService.login(loginDTO);
        return Result.success(userDTO);
    }

    /**
     * forgetPassword
     * @param request
     * @return
     */
    @Operation(summary = "忘记密码")
    @PostMapping("/forgetPassword")
    public Result forgetPassword(@RequestBody ForgetPasswordDTO request){
        loginService.forgetPassword(request);
        return Result.success(true);
    }

    /**
     * 获取验证码
     * @param phoneNumber
     * @return
     */
    @Operation(summary = "获取验证码")
    @Parameters({
            @Parameter(name = "phoneNumber",description = "手机号",in = ParameterIn.QUERY)
    })
    @GetMapping("/getPhoneVerify")
    public Result getPhoneVerify(@RequestParam("phoneNumber")String phoneNumber){
        System.out.println("手机号"+phoneNumber);

        String phoneVerify = loginService.getPhoneVerify(phoneNumber);
        return Result.success(phoneVerify);
    }

    /**
     * 用户注册
     * @param userDTO
     * @return
     */
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        loginService.register(userDTO);
        return Result.success(true);
    }

}

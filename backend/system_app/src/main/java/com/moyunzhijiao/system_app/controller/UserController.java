package com.moyunzhijiao.system_app.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moyunzhijiao.system_app.common.Result;
import com.moyunzhijiao.system_app.controller.dto.*;
import com.moyunzhijiao.system_app.controller.dto.fonted.FeedbackInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.KnowledgeInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.ReasonInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.video.VideoListInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.CollectInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.user.UserInfo;
import com.moyunzhijiao.system_app.service.FeedbackService;
import com.moyunzhijiao.system_app.service.UserService;
import com.moyunzhijiao.system_app.service.collection.CollectService;
import com.moyunzhijiao.system_app.service.collection.ExerciseCollectionService;
import com.moyunzhijiao.system_app.service.collection.KnowledgeCollectionService;
import com.moyunzhijiao.system_app.service.collection.VideoCollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "用户接口")
@RequestMapping("/UserService")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ExerciseCollectionService exerciseCollectionService;
    @Autowired
    KnowledgeCollectionService knowledgeCollectionService;
    @Autowired
    VideoCollectionService videoCollectionService;
    @Autowired
    FeedbackService feedbackService;
    @Autowired
    CollectService collectService;

    /**
     * 获取用户信息
     * @param token
     * @return
     * !!!为实现找用户教师的功能
     */
    @Operation(summary = "获取用户信息")
    @Parameters({
            @Parameter(name = "userId",description = "ID 编号",in = ParameterIn.QUERY)
    })
    @GetMapping("/getPersonal")
    public Result getPersonal(@RequestHeader("Authorization") String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("获取用户信息"+userId);
        UserInfo userInfo = userService.getPersonal(userId);
        return Result.success(userInfo);
    }

    /**
     * 获取练习收藏
     * @param token
     * @param pageNumber
     * @return
     */
    @Operation(summary = "获取练习收藏")
    @Parameters({
            @Parameter(name = "pageNum",description = "页数",in = ParameterIn.QUERY)
    })
    @GetMapping("/getExerciseCollect/{pageNum}")
    public Result getExerciseCollect(@RequestHeader("Authorization") String token, @PathVariable("pageNum") Integer pageNumber){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("获取练习收藏"+userId+pageNumber);

        // 获取练习收藏列表
        List<CollectInfo> collectInfos = exerciseCollectionService.getExerciseCollect(userId, pageNumber, 8);

        return Result.success(collectInfos);
    }

    /**
     * 获取知识收藏
     * @param token
     * @param pageNumber
     * @return
     */
    @Operation(summary = "获取知识收藏")
    @Parameters({
            @Parameter(name = "userId",description = "ID 编号",in = ParameterIn.QUERY),
            @Parameter(name = "pageNum",description = "页数",in = ParameterIn.QUERY)
    })
    @GetMapping("/getKnowledgeCollect/{pageNum}")
    public Result getKnowledgeCollect(@RequestHeader("Authorization") String token, @PathVariable("pageNum") Integer pageNumber){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("获取知识收藏"+userId);

        // 获取知识收藏列表
        List<KnowledgeInfo> knowledgeInfos = knowledgeCollectionService.getKnowledgeCollect(userId, pageNumber, 8);

        return Result.success(knowledgeInfos);
    }

    /**
     * 获取视频收藏
     * @param token
     * @param pageNumber
     * @return
     */
    @Operation(summary = "获取视频收藏")
    @Parameters({
            @Parameter(name = "userId",description = "ID 编号",in = ParameterIn.QUERY),
            @Parameter(name = "pageNum",description = "页数",in = ParameterIn.QUERY)
    })
    @GetMapping("/getVideoCollect/{pageNum}")
    public Result getVideoCollect(@RequestHeader("Authorization") String token, @PathVariable("pageNum") Integer pageNumber){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("获取视频收藏"+userId);

        // 获取视频收藏列表
        List<VideoListInfo> videoListInfos = videoCollectionService.getVideoCollect(userId, pageNumber, 8);

        return Result.success(videoListInfos);
    }

    /**
     * 获取我的反馈
     * @param token
     * @return
     */
    @Operation(summary = "获取我的反馈")
    @GetMapping("/getMineFeedback")
    public Result getMineFeedback(@RequestHeader("Authorization") String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("获取我的反馈"+userId);

        // 获取我的反馈列表
        List<FeedbackInfo> feedbackInfos = feedbackService.getMineFeedback(userId);

        return Result.success(feedbackInfos);
    }

    /**
     * 获取我的消息!!!
     * @param token
     * @param messageType
     * @param pageNumber
     * @return
     */
    @Operation(summary = "获取我的消息")
    @GetMapping("/getMineMessage/{messageType}/{pageNum}")
    public Result getMineMessage(@RequestHeader("Authorization") String token, @PathVariable("messageType") Integer messageType, @PathVariable("pageNum") Integer pageNumber){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("获取我的消息"+userId);

        userService.getMineMessage(userId, messageType, pageNumber);

        return Result.success(true);
    }

    /**
     * 更改性别
     * @param genderDTO
     * @return
     */
    @Operation(summary = "更改性别")
    @PostMapping("/alterGender")
    public Result alterGender(@RequestBody GenderDTO genderDTO, @RequestHeader("Authorization") String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        genderDTO.setUserId(userId);
        System.out.println("性别"+genderDTO.getUserId()+genderDTO.getGender());

        Boolean ifSuccess = userService.alterGender(genderDTO);

        return Result.success(ifSuccess);
    }

    /**
     * 更改姓名
     * @param nameDTO
     * @return
     */
    @Operation(summary = "更改姓名")
    @PostMapping("/alterUserName")
    public Result alterUserName(@RequestBody NameDTO nameDTO, @RequestHeader("Authorization") String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        nameDTO.setUserId(userId);
        System.out.println("姓名"+nameDTO.getUserId()+nameDTO.getNewName());

        Boolean ifSuccess = userService.alterUserName(nameDTO);

        return Result.success(ifSuccess);
    }

    /**
     * 更改头像
     * @param avatarDTO
     * @return
     */
    @Operation(summary = "更改头像")
    @PostMapping("/alterAvatar")
    public Result alterAvatar(@RequestBody AvatarDTO avatarDTO, @RequestHeader("Authorization") String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        avatarDTO.setUserId(userId);
        System.out.println("头像"+avatarDTO.getUserId()+avatarDTO.getAvatar());

        Boolean ifSuccess = userService.alterAvatar(avatarDTO);

        return Result.success(ifSuccess);
    }

    /**
     * 更改邮箱
     * @param emailDTO
     * @return
     */
    @Operation(summary = "更改邮箱")
    @PostMapping("/alterEmail")
    public Result alterEmail(@RequestBody EmailDTO emailDTO, @RequestHeader("Authorization") String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        emailDTO.setUserId(userId);
        System.out.println("邮箱"+emailDTO.getUserId()+emailDTO.getEmail());

        Boolean ifSuccess = userService.alterEmail(emailDTO);

        return Result.success(ifSuccess);
    }

    /**
     * 更改手机号
     * @param phoneDTO
     * @return
     */
    @Operation(summary = "更改手机号")
    @PostMapping("/alterPhoneNumber")
    public Result alterPhoneNumber(@RequestBody PhoneDTO phoneDTO, @RequestHeader("Authorization") String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        phoneDTO.setUserId(userId);
        System.out.println("手机号"+phoneDTO.getUserId()+phoneDTO.getNewPhoneNumber());

        Boolean ifSuccess = userService.alterPhoneNumber(phoneDTO);

        return Result.success(ifSuccess);
    }

    /**
     * 更改密码
     * @param passwordDTO
     * @return
     */
    @Operation(summary = "更改密码")
    @PostMapping("/alterPassword")
    public Result alterPassword(@RequestBody PasswordDTO passwordDTO, @RequestHeader("Authorization") String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        passwordDTO.setUserId(userId);
        System.out.println("密码"+passwordDTO.getUserId()+passwordDTO.getOldPassword()+passwordDTO.getNewPassword());

        ReasonInfo reason = userService.alterPassword(passwordDTO);

        return Result.success(reason);
    }

    /**
     * 上传反馈
     * @param feedbackDTO
     * @return
     */
    @Operation(summary = "上传反馈")
    @PostMapping("/uploadFeedback")
    public Result uploadFeedback(@RequestBody FeedbackDTO feedbackDTO, @RequestHeader("Authorization") String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        feedbackDTO.setUserId(userId);
        System.out.println("反馈"+feedbackDTO.getUserId()+feedbackDTO.getContent()+feedbackDTO.getType());

        Boolean ifSuccess = feedbackService.uploadFeedback(feedbackDTO, "学生");

        return Result.success(ifSuccess);
    }

    /**
     * 上传收藏
     * @param collectDTO
     * @return
     */
    @Operation(summary = "上传收藏")
    @PostMapping("/updateCollect")
    public Result updateCollect(@RequestBody CollectDTO collectDTO, @RequestHeader("Authorization") String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        collectDTO.setUserId(userId);
        System.out.println("收藏"+collectDTO.getUserId()+collectDTO.getCollectType()+collectDTO.getCollectId()+collectDTO.getNewCollect());

        boolean ifSuccess = collectService.updateCollect(collectDTO);

        return Result.success(ifSuccess);
    }
}

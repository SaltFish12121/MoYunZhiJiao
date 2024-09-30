package com.moyunzhijiao.system_app.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moyunzhijiao.system_app.common.Result;
import com.moyunzhijiao.system_app.controller.dto.CreateDTO;
import com.moyunzhijiao.system_app.controller.dto.Generate;
import com.moyunzhijiao.system_app.controller.dto.UploadDTO;
import com.moyunzhijiao.system_app.controller.dto.fonted.*;
import com.moyunzhijiao.system_app.controller.dto.fonted.excellent.ExcellentCompetitionInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.excellent.ExcellentHomeworkInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.excellent.ExcellentWorkInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.exercise.*;
import com.moyunzhijiao.system_app.service.ExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Tag(name = "练习接口")
@RequestMapping("/ExerciseService")
public class ExerciseController {
    @Autowired
    ExerciseService exerciseService;

    @Operation(summary = "获取学校练习")
    @GetMapping("/getSchoolExercise/{pageNum}")
    public Result getSchoolExercise(@RequestHeader("Authorization") String token, @RequestParam("type") String type, @PathVariable("pageNum") Integer pageNum) {
        // 解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("学校练习" + userId + type + pageNum);

        // 获取练习信息
        List<ExerciseInfo> exerciseInfos = exerciseService.getSchoolExercise(userId, type, pageNum, 8);

        // 返回结果
        return Result.success(exerciseInfos);
    }



    @Operation(summary = "获取自我练习")
    @GetMapping("/getMineExercise/{pageNum}")
    public Result getMineExercise(@RequestHeader("Authorization")String token, @RequestParam("type")String type, @PathVariable("pageNum")Integer pageNum){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("自我练习"+userId+type+pageNum);

        // 获取练习信息
        List<ExerciseInfo> exerciseInfos = exerciseService.getSelfExercise(userId, type, pageNum, 8);

        return Result.success(exerciseInfos);
    }


    @Operation(summary = "获取优秀作品")
    @GetMapping("/getExcellentExercise/{pageNum}")
    public Result getExcellentExercise(@RequestHeader("Authorization")String token, @PathVariable("pageNum")Integer pageNum){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("优秀作品"+userId+pageNum);

        // 获取优秀作品信息
        List<ExcellentWorkInfo> excellentWorkInfos = exerciseService.getExcellentExercise(pageNum, 8);

        return Result.success(excellentWorkInfos);
    }


    @Operation(summary = "获取系统模板")
    @GetMapping("/getSystemTemplate/{pageNum}")
    public Result getSystemTemplate(@PathVariable("pageNum")Integer pageNum){
        System.out.println("系统模板"+pageNum);

        // 获取系统模板信息
        List<SystemTemplateInfo> templates = exerciseService.getSystemTemplate(pageNum, 8);

        return Result.success(templates);
    }


    @Operation(summary = "获取已有练习")
    @GetMapping("/getExistExercise/{pageNum}")
    public Result getExistExercise(@RequestHeader("Authorization")String token, @PathVariable("pageNum")Integer pageNum){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("已有练习"+userId+pageNum);

        // 获取系统模板信息
        List<SystemTemplateInfo> existExercises = exerciseService.getExistExercise(userId, pageNum, 8);

        return Result.success(existExercises);
    }


    @Operation(summary = "获取专家字帖")
    @GetMapping("/getExpertCopybook/{pageNum}")
    public Result getExpertCopybook(@PathVariable("pageNum")Integer pageNum){
        System.out.println("专家字帖"+pageNum);

        return Result.success(true);
    }


    @Operation(summary = "获取样本字")
    @GetMapping("/getTemplateWord/{pageNum}")
    public Result getTemplateWord(@RequestParam(required = false)String search, @RequestParam(value = "radical", required = false)String radical, @RequestParam(value = "structure", required = false)String structure, @RequestParam(value = "typeface", required = false)String typeface,
                                @PathVariable("pageNum")Integer pageNum){
        System.out.println("样本字"+search+radical+structure+typeface+pageNum);

        // 调用 getSampleWord 方法获取样本字列表
        List<WordListInfo> sampleWords = exerciseService.getTemplateWord(search, radical, structure, typeface, pageNum, 20);

        return Result.success(sampleWords);
    }


    @Operation(summary = "获取所选单字")
    @GetMapping("/getSelectWord/{id}")
    public Result getSelectWord(@PathVariable("id")Integer id){
        System.out.println("所选单字"+id);

        WordListInfo selectWord = exerciseService.getSelectWord(id);

        return Result.success(selectWord);
    }


    @Operation(summary = "获取学校已完成详情")
    @GetMapping("/getSchoolFinish/{exerciseId}")
    public Result getSchoolFinish(@RequestHeader("Authorization")String token, @PathVariable("exerciseId")Integer exerciseId){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));

        System.out.println("学校已完成详情"+userId+exerciseId);

        SchoolFinishExerciseDetailInfo schoolFinishExerciseDetailInfo = exerciseService.getSchoolFinish(userId, exerciseId);

        return Result.success(schoolFinishExerciseDetailInfo);
    }


    @Operation(summary = "获取学校未完成详情")
    @GetMapping("/getSchoolUnfinish/{exerciseId}")
    public Result getSchoolUnfinish(@RequestHeader("Authorization")String token, @PathVariable("exerciseId")Integer exerciseId){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));

        System.out.println("学校未完成详情"+userId+exerciseId);

        SchoolUnfinishExerciseDetailInfo schoolUnfinishExerciseDetailInfo = exerciseService.getSchoolUnfinish(userId, exerciseId);

        return Result.success(schoolUnfinishExerciseDetailInfo);
    }


    @Operation(summary = "获取自我已完成详情")
    @GetMapping("/getMineFinish/{exerciseId}")
    public Result getMineFinish(@RequestHeader("Authorization")String token, @PathVariable("exerciseId")Integer exerciseId){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));

        System.out.println("自我已完成详情"+userId+exerciseId);

        SelfFinishExerciseDetailInfo selfFinishExerciseDetailInfo = exerciseService.getMineFinish(userId, exerciseId);

        return Result.success(selfFinishExerciseDetailInfo);
    }


    @Operation(summary = "获取自我未完成详情")
    @GetMapping("/getMineUnfinish/{exerciseId}")
    public Result getMineUnfinish(@RequestHeader("Authorization")String token, @PathVariable("exerciseId")Integer exerciseId){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));

        System.out.println("自我未完成详情"+userId+exerciseId);

        SelfUnfinishExerciseDetailInfo selfUnfinishExerciseDetailInfo = exerciseService.getMineUnfinish(userId, exerciseId);

        return Result.success(selfUnfinishExerciseDetailInfo);
    }


    @Operation(summary = "获取优秀学校练习详情")
    @GetMapping("/getExcellentHomework/{exerciseId}")
    public Result getExcellentHomework(@RequestHeader("Authorization")String token, @PathVariable("exerciseId")Integer exerciseId){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));

        System.out.println("优秀学校练习详情"+userId+exerciseId);

        ExcellentHomeworkInfo excellentHomeworkInfo = exerciseService.getExcellentHomework(userId, exerciseId);

        return Result.success(excellentHomeworkInfo);
    }


    @Operation(summary = "获取优秀竞赛练习详情")
    @GetMapping("/getExcellentCompetition/{exerciseId}")
    public Result getExcellentCompetition(@RequestHeader("Authorization")String token, @PathVariable("exerciseId")Integer competitionId){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));

        System.out.println("优秀竞赛练习详情"+userId+competitionId);

        ExcellentCompetitionInfo excellentCompetitionInfo = exerciseService.getExcellentCompetition(userId, competitionId);

        return Result.success(excellentCompetitionInfo);
    }


    @Operation(summary = "上传练习")
    @PostMapping("/uploadExercise")
    public Result uploadExercise(@RequestBody UploadDTO uploadDTO, @RequestHeader("Authorization")String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        uploadDTO.setUserId(userId);
        System.out.println("上传练习"+uploadDTO.getUserId()+uploadDTO.getExerciseId()+ Arrays.toString(uploadDTO.getContent()));

        Boolean ifSuccess = exerciseService.uploadExercise(uploadDTO);

        return Result.success(ifSuccess);
    }


    @Operation(summary = "创建练习")
    @PostMapping("/createExercise")
    public Result createExercise(@RequestBody CreateDTO createDTO, @RequestHeader("Authorization")String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        createDTO.setUserId(userId);
        System.out.println("创建练习"+createDTO);

        Boolean ifSuccess = exerciseService.createExercise(createDTO);

        return Result.success(ifSuccess);
    }


    @Operation(summary = "生成练习-专项")
    @PostMapping("/generateExercise")
    public Result generateExercise(@RequestBody WordListInfo[] wordListInfos){

        System.out.println("生成练习专项预览图"+wordListInfos);

        String exerciseImage = exerciseService.generateExercise(wordListInfos);

        return Result.success(exerciseImage);
    }


    @Operation(summary = "生成练习-综合")
    @PostMapping("/generateExercise2")
    public Result generateExercise2(@RequestBody Generate generate){

        System.out.println("生成练习综合预览图"+generate);

        String exerciseImage = exerciseService.generateExercise2(generate);

        return Result.success(exerciseImage);
    }
}

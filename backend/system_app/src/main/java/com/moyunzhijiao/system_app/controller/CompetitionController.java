package com.moyunzhijiao.system_app.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moyunzhijiao.system_app.common.Result;
import com.moyunzhijiao.system_app.controller.dto.CreateDTO;
import com.moyunzhijiao.system_app.controller.dto.SignUpDTO;
import com.moyunzhijiao.system_app.controller.dto.SubmitDTO;
import com.moyunzhijiao.system_app.controller.dto.fonted.competition.CompetitionDetailInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.competition.CompetitionInfo;
import com.moyunzhijiao.system_app.service.CompetitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "竞赛接口")
@RequestMapping("/CompetitionService")
public class CompetitionController {
    @Autowired
    CompetitionService competitionService;


    @Operation(summary = "获取竞赛信息（可提交的竞赛而非信息展示）")
    @GetMapping("/getCompetition")
    public Result getCompetition(@RequestHeader("Authorization")String token, @RequestParam("type")String type){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));

        System.out.println("获取竞赛信息"+userId+type);

        List<CompetitionInfo> competitionInfos = competitionService.getCompetition(userId, type);

        return Result.success(competitionInfos);
    }


    @Operation(summary = "获取竞赛详细信息（可提交的竞赛而非信息展示）")
    @GetMapping("/getCompetitionDetail/{exerciseId}")
    public Result getCompetitionDetail(@RequestHeader("Authorization")String token, @PathVariable("exerciseId")Integer exerciseId){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));

        System.out.println("获取竞赛详细信息"+userId+exerciseId);

        CompetitionDetailInfo competitionDetailInfo = competitionService.getCompetitionDetail(userId, exerciseId);

        return Result.success(competitionDetailInfo);
    }


    @Operation(summary = "竞赛报名")
    @PostMapping("/signUpCompetition")
    public Result signUpCompetition(@RequestHeader("Authorization")String token, @RequestBody SignUpDTO signUpDTO){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        signUpDTO.setUserId(userId);
        System.out.println("竞赛报名"+signUpDTO);

        Boolean ifSuccess = competitionService.signUpCompetition(signUpDTO);

        return Result.success(ifSuccess);
    }


    @Operation(summary = "竞赛提交")
    @PostMapping("/submitCompetition")
    public Result submitCompetition(@RequestBody SubmitDTO submitDTO){

        System.out.println("竞赛提交"+submitDTO);

        Boolean ifSuccess = competitionService.submitCompetition(submitDTO);

        return Result.success(ifSuccess);
    }
}

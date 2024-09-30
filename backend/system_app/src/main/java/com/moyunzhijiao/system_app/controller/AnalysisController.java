package com.moyunzhijiao.system_app.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moyunzhijiao.system_app.common.Result;
import com.moyunzhijiao.system_app.controller.dto.fonted.SubmitWritingInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.analysis.ExerciseAnalysisInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.analysis.WordAnalysisInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.analysis.WordAnalysisListInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.user.UserInfo;
import com.moyunzhijiao.system_app.service.AnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Tag(name = "数据分析接口")
@RequestMapping("/UserService")
public class AnalysisController {
    @Autowired
    AnalysisService analysisService;

    @Operation(summary = "获取练习数据分析")
    @GetMapping("/getExerciseAnalysis")
    public Result getExerciseAnalysis(@RequestHeader("Authorization") String token, @RequestParam String year, @RequestParam String month){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("获取练习数据分析"+userId+year+month);

        List<ExerciseAnalysisInfo> exerciseAnalysisInfos = analysisService.getExerciseAnalysis(userId, year, month);

        return Result.success(exerciseAnalysisInfos);
    }


    @Operation(summary = "获取所有字分析列表")
    @GetMapping("/getWordAnalysisList/{pageNum}")
    public Result getWordAnalysisList(@RequestHeader("Authorization") String token, @RequestParam(required = false) String search, @RequestParam(required = false) String radical, @RequestParam(required = false) String structure, @RequestParam(required = false) String typeface, @PathVariable("pageNum") Integer pageNum){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("获取所有字分析列表"+userId+search+radical+structure+typeface+pageNum);

        List<WordAnalysisListInfo> wordAnalysisListInfos = analysisService.getWordAnalysisList(userId, search, radical, structure, typeface, pageNum, 10);

        return Result.success(wordAnalysisListInfos);
    }


    @Operation(summary = "获取单字分析")
    @GetMapping("/getSingleWordAnalysis/{wordId}")
    public Result getSingleWordAnalysis(@RequestHeader("Authorization") String token, @PathVariable("wordId") Integer wordId){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("获取单字分析"+userId+wordId);

        WordAnalysisInfo wordAnalysisListInfo = analysisService.getSingleWordAnalysis(userId, wordId);

//        System.out.println("Score ALL: " + Arrays.toString(wordAnalysisListInfo.getScore())); // 添加日志

        return Result.success(wordAnalysisListInfo);
    }

}

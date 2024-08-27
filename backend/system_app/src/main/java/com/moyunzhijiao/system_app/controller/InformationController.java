package com.moyunzhijiao.system_app.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moyunzhijiao.system_app.common.Result;
import com.moyunzhijiao.system_app.controller.dto.fonted.competition.CompetitionInformInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.KnowledgeInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.ProblemInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.video.VideoCollectionInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.video.VideoContentInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.video.VideoInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.video.VideoListInfo;
import com.moyunzhijiao.system_app.service.InformationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "信息接口")
@RequestMapping("/InformationService")
public class InformationController {
    @Autowired
    InformationService informationService;

    /**
     * 获取常见问题
     * @return
     */
    @Operation(summary = "获取常见问题")
    @GetMapping("/getCommonProblem")
    public Result getCommonProblem() {
        System.out.println("常见问题");

        List<ProblemInfo> commonProblems = informationService.getCommonProblem();

        return Result.success(commonProblems);
    }

    /**
     * 获取首页知识展示
     * @param token
     * @return
     */
    @Operation(summary = "获取首页知识展示")
    @GetMapping("/getKnowledgeShow")
    public Result getKnowledgeShow(@RequestHeader("Authorization") String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("获取首页知识展示"+userId);

        List<KnowledgeInfo> knowledgeInfos = informationService.getKnowledgeShow(userId);

        return Result.success(knowledgeInfos);
    }

    /**
     * 获取首页视频展示
     * @param token
     * @return
     */
    @Operation(summary = "获取首页视频展示")
    @GetMapping("/getVideoShow")
    public Result getVideoShow(@RequestHeader("Authorization") String token) {
        System.out.println("获取首页视频展示");

        List<VideoListInfo> videoList = informationService.getVideoShow();

        return Result.success(videoList);
    }

    /**
     * 获取首页竞赛展示
     * @param token
     * @return
     */
    @Operation(summary = "获取首页竞赛展示")
    @GetMapping("/getCompetitionShow")
    public Result getCompetitionShow(@RequestHeader("Authorization") String token){
        System.out.println("获取首页竞赛展示");

        List<CompetitionInformInfo> competitionInformInfos = informationService.getCompetitionShow();

        return Result.success(competitionInformInfos);
    }

    /**
     * 获取知识
     * @param search
     * @param type
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @Operation(summary = "获取知识")
    @Parameters({
            @Parameter(name = "search",description = "搜索",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "type",description = "类型",in = ParameterIn.QUERY),
            @Parameter(name = "pageNum",description = "页数",in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页传输数",in = ParameterIn.QUERY),
    })
    @GetMapping("/getKnowledge/{pageNum}/{pageSize}")
    public Result getKnowledge(@RequestHeader("Authorization") String token, @RequestParam(required = false)String search, @RequestParam("type")String type, @PathVariable("pageSize") Integer pageSize, @PathVariable("pageNum") Integer pageNumber){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("知识"+userId+search+type+pageNumber+pageSize);

        List<KnowledgeInfo> knowledgeInfos = informationService.getKnowledge(search, type, pageNumber ,pageSize, userId);

        return Result.success(knowledgeInfos);
    }

    /**
     * 获取书法教学
     * @param search
     * @param pageNumber
     * @return
     */
    @Operation(summary = "获取书法教学")
    @Parameters({
            @Parameter(name = "search",description = "搜索",in = ParameterIn.QUERY),
            @Parameter(name = "pageNum",description = "页数",in = ParameterIn.QUERY),
    })
    @GetMapping("/getTeachingVideo/{pageNum}")
    public Result getTeachingVideo(@RequestParam(required = false)String search, @PathVariable("pageNum") Integer pageNumber){
        System.out.println("知识"+search+pageNumber);

        List<VideoListInfo> videoListInfos = informationService.getTeachingVideo(search, pageNumber, 8);

        return Result.success(videoListInfos);
    }

    /**
     * 获取单独书法教学
     * @param videoId
     * @return
     */
    @Operation(summary = "获取单独书法教学")
    @GetMapping("/getTeachingSingleVideo/{videoId}")
    public Result getTeachingSingleVideo(@PathVariable("videoId")Integer videoId, @RequestHeader("Authorization") String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("书法单独教学"+userId+videoId);

        VideoInfo videoInfo = informationService.getTeachingSingleVideo(videoId, userId);

        return Result.success(videoInfo);
    }

    /**
     * 获取书法教学合集
     * @param videoCollectionId
     * @return
     */
    @Operation(summary = "获取书法教学合集")
    @GetMapping("/getTeachingCollectionVideo/{videoId}")
    public Result getTeachingCollectionVideo(@PathVariable("videoId")Integer videoCollectionId, @RequestHeader("Authorization") String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        System.out.println("书法教学合集"+userId+videoCollectionId);

        VideoCollectionInfo videoCollectionInfo = informationService.getTeachingCollectionVideo(videoCollectionId, userId);

        return Result.success(videoCollectionInfo);
    }

    /**
     * 获取视频
     * @param videoDetailId
     * @return
     */
    @Operation(summary = "获取视频")
    @GetMapping("/getVideo/{videoDetailId}")
    public Result getVideo(@PathVariable("videoDetailId")Integer videoDetailId){
        System.out.println("视频"+videoDetailId);

        VideoContentInfo videoContentInfo = informationService.getVideoContent(videoDetailId);

        return Result.success(videoContentInfo);
    }

    /**
     * 获取竞赛通知
     * @return
     */
    @Operation(summary = "获取竞赛通知")
    @GetMapping("/getCompetition")
    public Result getCompetition(){
        System.out.println("竞赛通知");

        List<CompetitionInformInfo> competitionInformInfos = informationService.getCompetition();

        return Result.success(competitionInformInfos);
    }

    /**
     * 获取竞赛通知
     * @return
     */
    @Operation(summary = "获取单个竞赛通知")
    @GetMapping("/getCompetitionSingle/{competitionId}")
    public Result getCompetitionSingle(@PathVariable("competitionId")Integer competitionId){
        System.out.println("获取单个竞赛通知"+competitionId);

        CompetitionInformInfo competitionInformInfo = informationService.getCompetitionSingle(competitionId);

        return Result.success(competitionInformInfo);
    }

    /**
     * 获取各种协议
     * @param type
     * @return
     */
    @Operation(summary = "获取各种协议")
    @GetMapping("/getAgreement")
    public Result getAgreement(@RequestParam("type")String type){
        System.out.println("各种协议"+type);

        String agreement = informationService.getAgreement(type);

        return Result.success(agreement);
    }


}

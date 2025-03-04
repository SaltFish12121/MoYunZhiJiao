package com.moyunzhijiao.system_backend.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Getter
public class ConfigService {
    @Value("${server.protocol}")
    private String serverProtocol;      //协议，例如http

    @Value("${server.ip}")
    private String serverIp;            //服务器ip地址

    @Value("${server.port}")
    private String serverPort;          //端口

    private static String baseUrl;

    //FileController里的也要改
    //模板字的API接口
    private static String templateWordFilePath;
    private static String templateWordUrl;
    private static final String TEMPLATE_WORD_BASE_URL = "/upload/images/templateWord";
    private static final String TEMPLATE_WORD_PATH = "/resources/image/templateWord/";

    //样本字的
    private static String sampleWordFilePath;
    private static String sampleWordUrl;
    private static final String SAMPLE_WORD_BASE_URL = "/upload/images/sampleWord";
    private static final String SAMPLE_WORD_PATH = "/resources/image/sampleWord/";


    //字帖的
    private static String copybookFilePath;
    private static String copybookUrl;
    private static final String COPYBOOK_BASE_URL = "/upload/images/copybook";
    private static final String COPYBOOK_PATH = "/resources/image/copybook/";

    //视频的
    private static String videoFilePath;
    private static String videoUrl;
    private static final String VIDEO_BASE_URL = "/upload/videos";
    private static final String VIDEO_PATH = "/resources/video/";

    //视频封面的
    private static String videoImageFilePath;
    private static String videoImageUrl;
    private static final String VIDEO_IMAGE_BASE_URL = "/upload/images/video";
    private static final String VIDEO_IMAGE_PATH = "/resources/image/video/";
    //字的分析的
    private static String characterFilePath;
    private static String characterUrl;
    private static final String CHARACTER_BASE_URL = "/upload/images/character";
    private static final String CHARACTER_PATH = "/resources/image/character/";

    //笔画分析的
    private static String strokeFilePath;
    private static String strokeUrl;
    private static final String STROKE_BASE_URL = "/upload/images/stroke";
    private static final String STROKE_PATH = "/resources/image/stroke/";

    //作业图片的
    private static String homeworkFilePath;
    private static String homeworkUrl;
    private static final String HOMEWORK_BASE_URL = "/upload/images/homework";
    private static final String HOMEWORK_PATH = "/resources/image/homework/";

    @PostConstruct
    public void init() {
        baseUrl = serverProtocol + "://" + serverIp + ":" + serverPort;
        File projectRoot = new File(System.getProperty("user.dir"));


        //设置模板字
        templateWordFilePath = projectRoot.getParentFile().getParent()+TEMPLATE_WORD_PATH;
        templateWordUrl = baseUrl+TEMPLATE_WORD_BASE_URL;

        //设置样本字
        sampleWordFilePath = projectRoot.getParentFile().getParent()+SAMPLE_WORD_PATH;
        sampleWordUrl = baseUrl+SAMPLE_WORD_BASE_URL;

        //设置字帖
        copybookFilePath = projectRoot.getParentFile().getParent()+COPYBOOK_PATH;
        copybookUrl = baseUrl+COPYBOOK_BASE_URL;


        //视频的
        videoFilePath = projectRoot.getParentFile().getParent()+VIDEO_PATH;
        videoUrl = baseUrl+VIDEO_BASE_URL;
        //视频封面的
        videoImageFilePath = projectRoot.getParentFile().getParent()+VIDEO_IMAGE_PATH;
        videoImageUrl = baseUrl+VIDEO_IMAGE_BASE_URL;

        //字的分析的
        characterFilePath = projectRoot.getParentFile().getParent()+CHARACTER_PATH;
        characterUrl = baseUrl+CHARACTER_BASE_URL;

        //笔画分析的
        strokeFilePath = projectRoot.getParentFile().getParent()+STROKE_PATH;
        strokeUrl = baseUrl+STROKE_BASE_URL;

        //作业图片的
        homeworkFilePath = projectRoot.getParentFile().getParent()+HOMEWORK_PATH;
        homeworkUrl = baseUrl+HOMEWORK_BASE_URL;
    }
    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getTemplateWordFilePath() {
        return templateWordFilePath;
    }

    public static String getSampleWordFilePath(){
        return sampleWordFilePath;
    }

    public static String getCopybookFilePath(){
        return copybookFilePath;
    }

    public static String getCopybookUrl(){
        return copybookUrl;
    }

    public static String getSampleWordUrl(){
        return sampleWordUrl;
    }
    public static String getTemplateWordUrl(){
        return templateWordUrl;
    }

    public static String getVideoFilePath(){
        return videoFilePath;
    }
    public static String getVideoUrl(){
        return videoUrl;
    }
    public static String getVideoImageFilePath(){
        return videoImageFilePath;
    }
    public static String getVideoImageUrl(){
        return videoImageUrl;
    }
    public static String getCharacterFilePath(){
        return characterFilePath;
    }

    public static String getStrokeFilePath(){
        return strokeFilePath;
    }
    public static String getHomeworkFilePath(){
        return homeworkFilePath;
    }


}

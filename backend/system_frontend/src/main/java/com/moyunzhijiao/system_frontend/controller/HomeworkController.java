package com.moyunzhijiao.system_frontend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkDTO;
import com.moyunzhijiao.system_frontend.controller.dto.KlassHomeworkDetailDTO;
import com.moyunzhijiao.system_frontend.controller.dto.PublishByTemplateDTO;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.service.homework.HomeworkService;
import com.moyunzhijiao.system_frontend.service.homework.TeacherHomeworkService;
import com.moyunzhijiao.system_frontend.service.note.KlassNoteReceiveService;
import com.moyunzhijiao.system_frontend.service.note.StudentNoteReceiveService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class HomeworkController {
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    TeacherHomeworkService teacherHomeworkService;
    @Autowired
    KlassNoteReceiveService klassNoteReceiveService;
    @Autowired
    StudentNoteReceiveService studentNoteReceiveService;

    @Operation(summary = "教师的查询作业列表，四个分页查询")
    @GetMapping("/ciep/homework")
    public Result findHomeworkPageOfTeacher(@RequestHeader("authorization") String token
            , @RequestParam("") Integer type, @RequestParam Integer currentPage, @RequestParam Integer pageSize
            ,@RequestParam(defaultValue = "")String name){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        IPage<HomeworkDTO> page = new Page<>(currentPage,pageSize);
        page = teacherHomeworkService.getHomeworkPageOfTeacher(page,type,teacherId,name);
        return Result.success(page);
    }

    @Operation(summary = "延迟作业")
    @PostMapping("/ciep/postpone")
    public Result postponeHomework(@RequestBody Map<String, String> request) throws ParseException {
        String homeworkId = request.get("homeworkId");
        String newDate = request.get("newDate");
        Homework homework ;
        homework = homeworkService.getById(homeworkId);
        //字符串能直接修改数据库的时间数据类型
        homework.setDeadline(newDate);
        homeworkService.updateById(homework);
        return Result.success();
    }

    @Operation(summary = "催促作业，-1表示没有,type中0表示集体作业催促，1表示个人作业催促")
    @PostMapping("/ciep/urge")
    public Result urgeHomework(@RequestHeader("authorization") String token,@RequestBody Map<String, String> request){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        Integer homeworkId = Integer.valueOf(request.get("homeworkId"));
        //检查作业
        if(homeworkId==-1){
            return Result.error(Constants.CODE_401,"未指定作业！");
        }
        int stuId = Integer.parseInt(request.get("stuId"));
        int type = Integer.parseInt(request.get("type"));
        try {
            if (stuId!=-1){
                studentNoteReceiveService.urgeHomeworkSingle(teacherId,stuId,homeworkService.getById(homeworkId));
            } else {
                switch (type){
                    case 0:
                        klassNoteReceiveService.urgeHomework(teacherId,homeworkService.getById(homeworkId));
                        break;
                    case 1:
                        studentNoteReceiveService.urgeHomeworkBatch(teacherId,homeworkService.getById(homeworkId));
                        break;
                    default:
                        return Result.error(Constants.CODE_401,"type内容错误！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(Constants.CODE_500,"系统错误，催促失败！");
        }
        return Result.success();
    }

    @Operation(summary = "获取作业详情（班级）")
    @GetMapping("/ciep/class-homework-detail")
    public Result detailInKlass(@RequestParam Integer homeworkId, @RequestParam Integer currentPage, @RequestParam Integer pageSize){
        KlassHomeworkDetailDTO klassHomeworkDetailDTO;
        klassHomeworkDetailDTO = homeworkService.getHomeworkDetail(homeworkId,currentPage,pageSize);
        return Result.success(klassHomeworkDetailDTO);
    }

    @Operation(summary = "教师根据模板发布作业")
    @PostMapping("/ciep/publish-homework")
    public Result publishHomeworkByTemplate(@RequestHeader("authorization") String token,@RequestBody PublishByTemplateDTO publishByTemplateDTO){
        System.out.println("让我看下类长什么样"+publishByTemplateDTO.toString());
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        //从封装的类中取出数据
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        String templateType = publishByTemplateDTO.getTemplateType();
        Integer templateId = publishByTemplateDTO.getTemplateId();
        String name = publishByTemplateDTO.getDescription().getName();
        String target = publishByTemplateDTO.getDescription().getTarget();
        String require = publishByTemplateDTO.getDescription().getRequire();
        String deadline = publishByTemplateDTO.getDescription().getDeadline();
        List<Integer> list = publishByTemplateDTO.getList();
        //根据模板新增作业
        homeworkService.addByTemplate(teacherId,templateId,templateType,require,name,target,deadline,list);
        return Result.success();
    }

}

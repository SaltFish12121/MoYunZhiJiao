package com.moyunzhijiao.system_backend.controller;

import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.Font;
import com.moyunzhijiao.system_backend.service.FontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/backend/font")
public class FontController {
    @Autowired
    FontService fontService;
    @GetMapping("/fonts")
    public Result findAllFonts(){
        List<Font> list = fontService.list();
        return Result.success(list);
    }
}

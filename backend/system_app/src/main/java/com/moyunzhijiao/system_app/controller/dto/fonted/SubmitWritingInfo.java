package com.moyunzhijiao.system_app.controller.dto.fonted;

import lombok.Data;

@Data
public class SubmitWritingInfo {
    Integer id; //提交作业ID
    Object submit; //提交的图
    WordInfo[][] word;  //拆出的字

    public SubmitWritingInfo(Integer id, Object submit, WordInfo[][] word) {
        this.id = id;
        this.submit = submit;
        this.word = word;
    }

}

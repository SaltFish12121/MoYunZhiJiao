package com.moyunzhijiao.system_app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("question")
public class CommonProblem {
    @TableId
    Integer id;
    String q;
    String a;
    String editor;
    String title;
    String type;
}

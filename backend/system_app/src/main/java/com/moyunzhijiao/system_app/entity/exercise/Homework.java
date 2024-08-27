package com.moyunzhijiao.system_app.entity.exercise;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value="homework")
public class Homework {
    @TableId(value="id",type = IdType.AUTO)
    Integer id;
    String name;
    String type;
    @TableField("detail_type")
    String detailType;
    @TableField("font_id")
    Integer fontId;
    @TableField(value = "word_count")
    Integer wordCount;
    String requirements;
    String target;
    String deadline;
    Integer difficulty;
    @TableField("created_time")
    String createdTime;
    @TableField("is_self")
    Integer isSelf;
}

package com.moyunzhijiao.system_app.entity.word;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

//模板字
@Data
@TableName("template_word")
public class TemplateWord {
    @TableId
    Integer id;
    String name;
    @TableField("structure_id")
    Integer structureId;
    @TableField("font_id")
    Integer fontId;
    @TableField("radical_id")
    Integer radicalId;
    String content;
    String importer;
    Integer grade_id;
    String created_time;
    String author;
}

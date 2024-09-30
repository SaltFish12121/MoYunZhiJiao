package com.moyunzhijiao.system_app.entity.word;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sample_word")
public class SampleWord {
    Integer id;
    String name;
    @TableField("structure_id")
    Integer structureId;
    @TableField("radical_id")
    Integer radicalId;
    @TableField("grade_id")
    Integer gradeId;
    String content;
    String source;
    String created_time;
    String importer;
}

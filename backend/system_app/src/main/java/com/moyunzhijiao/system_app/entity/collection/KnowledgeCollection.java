package com.moyunzhijiao.system_app.entity.collection;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("knowledge_collection")
public class KnowledgeCollection {
    @TableId("student_id")
    Integer studentId;
    @TableField("resources_id")
    Integer resourcesId;
}

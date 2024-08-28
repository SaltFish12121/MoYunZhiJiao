package com.moyunzhijiao.system_app.entity.knowledge;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("article_contents")
public class KnowledgeContents {
    @TableId
    Integer id;
    String content;
}

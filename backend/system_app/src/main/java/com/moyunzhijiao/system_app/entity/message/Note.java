package com.moyunzhijiao.system_app.entity.message;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("note")
public class Note {
    @TableId
    Integer id;
    String name;
    String type;
    String sender;
    @TableField("association_id")
    Integer associationId;
    @TableField("created_time")
    String createdTime;
    String target;
}

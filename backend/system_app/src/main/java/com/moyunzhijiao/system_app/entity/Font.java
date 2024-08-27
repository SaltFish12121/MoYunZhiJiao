package com.moyunzhijiao.system_app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("font")
public class Font {
    @TableId
    Integer id;
    String name;
}

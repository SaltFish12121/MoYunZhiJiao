package com.moyunzhijiao.system_app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("structure")
public class Structure {
    Integer id;
    String name;
    String updated_time;
}

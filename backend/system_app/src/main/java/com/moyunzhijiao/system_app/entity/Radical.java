package com.moyunzhijiao.system_app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("radical")
public class Radical {
    Integer id;
    String name;
    String updated_time;
}

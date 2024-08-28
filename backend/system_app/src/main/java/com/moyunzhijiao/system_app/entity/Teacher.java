package com.moyunzhijiao.system_app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="teacher")
public class Teacher {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;

    String name;

    String workno;

    String password;

    @TableField("school_id")
    Integer schoolId;

    String phone;

    String email;

    @TableField("region_id")
    Integer regionId;

    @TableField("id_number")
    String idNumber;

    String gender;

    String avatar;

    @TableField("delete_flag")
    boolean deleteFlag;

    @TableField("created_time")
    String createdTime;

}

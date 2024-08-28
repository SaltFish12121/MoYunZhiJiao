package com.moyunzhijiao.system_app.controller.dto.fonted.user;

import lombok.Data;

@Data
public class RegionInfo {
    Integer id;  // 地区id
    String name;  // 地区名

    // 添加构造函数
    public RegionInfo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

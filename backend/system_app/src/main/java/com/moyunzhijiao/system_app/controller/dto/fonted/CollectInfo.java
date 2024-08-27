package com.moyunzhijiao.system_app.controller.dto.fonted;

import lombok.Data;

@Data
public class CollectInfo {
    Integer id;
    String type;
    String title;
    String time;

    public CollectInfo(Integer id, String type, String title, String time) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.time = time;
    }
}

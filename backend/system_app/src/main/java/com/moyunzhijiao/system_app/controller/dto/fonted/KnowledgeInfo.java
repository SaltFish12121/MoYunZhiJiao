package com.moyunzhijiao.system_app.controller.dto.fonted;

import lombok.Data;

@Data
public class KnowledgeInfo {
    Integer id;             // ID 编号
    String type;     // 知识类型
    Boolean ifRecommend;    // 是否为推荐知识
    String title;           // 标题
    Integer viewNumber;     // 观看数量
    String time;            // 创建时间
    String cover;           // 封面
    String content;         // 内容
    Boolean ifCollect;      // 是否收藏

    // 构造函数
    public KnowledgeInfo(Integer id, String type, Boolean ifRecommend, String title, Integer viewNumber, String time, String cover, String content, Boolean ifCollect) {
        this.id = id;
        this.type = type;
        this.ifRecommend = ifRecommend;
        this.title = title;
        this.viewNumber = viewNumber;
        this.time = time;
        this.cover = cover;
        this.content = content;
        this.ifCollect = ifCollect;
    }
}


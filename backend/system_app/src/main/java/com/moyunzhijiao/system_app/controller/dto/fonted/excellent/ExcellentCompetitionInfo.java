package com.moyunzhijiao.system_app.controller.dto.fonted.excellent;

import com.moyunzhijiao.system_app.controller.dto.fonted.competition.CompetitionContentInfo;
import lombok.Data;

import java.util.List;

@Data
public class ExcellentCompetitionInfo {
    int id;  // 收藏ID
    String title;  // 标题
    String comeFrom;  // 来源
    boolean ifCollect;  // 是否收藏

    List<CompetitionContentInfo> content;  // 竞赛展示

    public ExcellentCompetitionInfo(int id, String title, String comeFrom, boolean ifCollect,
                                    List<CompetitionContentInfo> content) {
        this.id = id;
        this.title = title;
        this.comeFrom = comeFrom;
        this.ifCollect = ifCollect;
        this.content = content;
    }

    // Getters and Setters
}


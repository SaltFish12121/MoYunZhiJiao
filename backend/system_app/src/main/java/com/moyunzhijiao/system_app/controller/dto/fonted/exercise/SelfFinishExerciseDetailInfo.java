package com.moyunzhijiao.system_app.controller.dto.fonted.exercise;

import com.moyunzhijiao.system_app.controller.dto.fonted.SubmitWritingInfo;
import lombok.Data;

import java.util.List;

@Data
public class SelfFinishExerciseDetailInfo {
    int id;  // 练习ID
    String title;  // 标题
    boolean ifCollect;  // 是否收藏
    String typeface;  // 字体
    String example;  // 样例
    List<SubmitWritingInfo> submit;  // 提交的图
    int score;  // 得分
    String systemComment;  // 系统评语

    public SelfFinishExerciseDetailInfo(int id, String title, boolean ifCollect,
                                        String typeface, String example,
                                        List<SubmitWritingInfo> submit, int score,
                                        String systemComment) {
        this.id = id;
        this.title = title;
        this.ifCollect = ifCollect;
        this.typeface = typeface;
        this.example = example;
        this.submit = submit;
        this.score = score;
        this.systemComment = systemComment;
    }
}

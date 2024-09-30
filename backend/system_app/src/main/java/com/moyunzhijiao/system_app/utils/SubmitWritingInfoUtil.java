package com.moyunzhijiao.system_app.utils;

import com.moyunzhijiao.system_app.controller.dto.fonted.SubmitWritingInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.WordInfo;
import com.moyunzhijiao.system_app.entity.competition.CsubmissionImage;
import com.moyunzhijiao.system_app.entity.exercise.HomeworkSubmission;
import com.moyunzhijiao.system_app.entity.exercise.HsubmissionImage;
import com.moyunzhijiao.system_app.mapper.exercise.HsubmissionImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SubmitWritingInfoUtil {
    @Autowired
    HsubmissionImageMapper hsubmissionImageMapper;

    public SubmitWritingInfo createSubmitWritingInfo(CsubmissionImage image, List<WordInfo> wordInfos) {
        return createSubmitWritingInfo(image.getSubmissionId(), image.getPictureUrl(), wordInfos);
    }

    public SubmitWritingInfo createSubmitWritingInfo(HomeworkSubmission submission, List<WordInfo> wordInfos) {
        HsubmissionImage hsubmissionImage = hsubmissionImageMapper.selectById(submission.getId());
        String pictureUrl = (hsubmissionImage != null) ? hsubmissionImage.getPictureUrl() : "https://dummyimage.com/120x240";
        return createSubmitWritingInfo(submission.getId(), pictureUrl, wordInfos);
    }

    private SubmitWritingInfo createSubmitWritingInfo(Integer submissionId, String pictureUrl, List<WordInfo> wordInfos) {
        // 创建一个默认的 WordInfo 对象
        WordInfo defaultWordInfo = new WordInfo(
                "default_word_url", // 默认的图片URL
                Arrays.asList("default_stroke_url"), // 默认的笔画图片URL
                0, // 默认的得分
                "default_template_word", // 默认的模板字
                "default_comment" // 默认的评论
        );

        // 计算行数和列数
        int rows = 12;
        int cols = 8;
        WordInfo[][] wordInfoArray = new WordInfo[rows][cols];

        // 填充二维数组
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int index = i * cols + j;
                if (index < wordInfos.size()) {
                    wordInfoArray[i][j] = wordInfos.get(index);
                } else {
                    wordInfoArray[i][j] = defaultWordInfo; // 填充默认数据
                }
            }
        }

        // 构造 SubmitWritingInfo 对象
        return new SubmitWritingInfo(
                submissionId,
                pictureUrl,
                wordInfoArray
        );
    }
}

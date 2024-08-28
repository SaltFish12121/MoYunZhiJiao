package com.moyunzhijiao.system_app.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_app.controller.dto.FeedbackDTO;
import com.moyunzhijiao.system_app.controller.dto.fonted.FeedbackInfo;
import com.moyunzhijiao.system_app.entity.Feedback;
import com.moyunzhijiao.system_app.entity.FeedbackContent;
import com.moyunzhijiao.system_app.mapper.FeedbackContentMapper;
import com.moyunzhijiao.system_app.mapper.FeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService extends ServiceImpl<FeedbackMapper, Feedback> {
    @Autowired
    FeedbackMapper feedbackMapper;

    @Autowired
    FeedbackContentMapper feedbackContentMapper;

    // 获取我的反馈
    public List<FeedbackInfo> getMineFeedback(Integer userId) {
        // 通过用户ID找到反馈表中的反馈记录
        QueryWrapper<Feedback> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("provider_id", userId);
        List<Feedback> feedbacks = feedbackMapper.selectList(queryWrapper);

        System.out.println("获取的反馈数据" + feedbacks);

        List<FeedbackInfo> results = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            if (feedback != null) {
                System.out.println("获取的FeedbackId" + feedback.getId());

                // 直接在这里查询反馈内容
                QueryWrapper<FeedbackContent> contentQueryWrapper = new QueryWrapper<>();
                contentQueryWrapper.eq("feedback_id", feedback.getId());
                FeedbackContent feedbackContent = feedbackContentMapper.selectOne(contentQueryWrapper);
                String content = feedbackContent != null ? feedbackContent.getMessage() : "内容未找到";
                String reply = feedbackContent != null ? feedbackContent.getReply() : "内容未找到";

                FeedbackInfo result = new FeedbackInfo(feedback.getId(), feedback.getType(), content, feedback.getCreatedTime(), reply);
                results.add(result);
            }
        }

        return results;
    }


    // 上传反馈
    public boolean uploadFeedback(FeedbackDTO feedbackDTO, String userType) {
        // 创建新的反馈对象
        Feedback feedback = new Feedback();
        feedback.setProviderId(feedbackDTO.getUserId());
        feedback.setType(feedbackDTO.getType());
        feedback.setProviderType(userType);

        // 插入反馈记录
        int feedbackResult = feedbackMapper.insert(feedback);

        if (feedbackResult > 0) {
            // 创建新的反馈内容对象
            FeedbackContent feedbackContent = new FeedbackContent();
            feedbackContent.setFeedbackId(feedback.getId());
            feedbackContent.setMessage(feedbackDTO.getContent());

            // 插入反馈内容记录
            int contentResult = feedbackContentMapper.insert(feedbackContent);

            return contentResult > 0;
        } else {
            return false;
        }
    }



}



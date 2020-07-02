package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Feedback;

import java.util.Map;

public interface FeedbackService {
    BaseData queryFeedback(Integer page, Integer limit, String sort, String order, String username, Integer id);

    void submitFeedBack(Feedback feedback);
}

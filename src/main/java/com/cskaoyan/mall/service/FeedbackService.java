package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;

public interface FeedbackService {
    BaseData queryFeedback(Integer page, Integer limit, String sort, String order, String username, Integer id);
}

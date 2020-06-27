package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;

public interface SearchHistoryService {
    BaseData queryHistory(Integer page, Integer limit, String sort, String order, Integer userId, String keyword);
}

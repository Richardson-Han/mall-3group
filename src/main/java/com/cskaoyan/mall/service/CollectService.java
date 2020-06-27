package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import org.springframework.stereotype.Service;


public interface CollectService {
    BaseData queryCollect(Integer page, Integer limit, String sort, String order, Integer userId, Integer valueId);
}

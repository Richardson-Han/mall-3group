package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;

public interface FootprintService {
    BaseData queryFootprint(Integer page, Integer limit, String sort, String order, Integer userId, Integer goodsId);
}

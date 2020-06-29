package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;

public interface LogService {
    BaseData getLogList(Integer page, Integer limit, String name, String sort, String order);
}

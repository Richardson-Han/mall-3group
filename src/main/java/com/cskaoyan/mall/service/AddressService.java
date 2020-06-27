package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;

public interface AddressService {
    BaseData queryAddress(Integer page, Integer limit, String sort, String order, String name, Integer userId);
}

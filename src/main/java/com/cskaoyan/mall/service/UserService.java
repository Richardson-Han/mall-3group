package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;

public interface UserService {

    BaseData queryUsers(Integer page,Integer limit,String sort,String order);
}

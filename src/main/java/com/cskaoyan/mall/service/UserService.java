package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.VO.StatBaseVO;

public interface UserService {

    BaseData queryUsers(Integer page, Integer limit, String sort, String order, String username, String mobile);

    Long getUserTotal();

    StatBaseVO getUserStat();
}

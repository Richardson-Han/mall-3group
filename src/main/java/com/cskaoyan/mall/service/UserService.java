package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.VO.StatBaseVO;

public interface UserService {

    BaseData queryUsers(Integer page, Integer limit, String sort, String order, String username, String mobile);

    Long getUserTotal();

    StatBaseVO getUserStat();

    Integer wxselectIdByUsername(String username);

    Integer wxinsertGainCoupon(Integer userId, Integer couponId, Integer total);
}

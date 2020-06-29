package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.VO.StatBaseVO;

public interface OrderService {
    Long getOrderTotal();

    StatBaseVO getOrderStat();
}

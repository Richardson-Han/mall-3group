package com.cskaoyan.mall.service;


import com.cskaoyan.mall.bean.VO.OrderRefundVO;
import com.cskaoyan.mall.bean.VO.ShipVO;
import com.cskaoyan.mall.bean.VO.StatBaseVO;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Long getOrderTotal();

    Map<String, Object> queryOrderPageList(Integer page, Integer limit, String sort, String order,
                                           List<Integer> orderStatusArray, Integer userId, Integer orderSn);

    StatBaseVO getOrderStat();

    Map<String ,Object> queryOrderDetailById(Integer id);

    void orderRefund(OrderRefundVO orderRefundVO);

    void orderShip(ShipVO shipVO);
}

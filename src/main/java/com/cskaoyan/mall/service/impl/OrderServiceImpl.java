package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.OrderExample;
import com.cskaoyan.mall.bean.OrderStat;
import com.cskaoyan.mall.bean.VO.StatBaseVO;
import com.cskaoyan.mall.mapper.OrderMapper;
import com.cskaoyan.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/***
 * @author 社会主义好
 * @date 2020-06-26 17:21 星期五 
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    /**
     *
     * @return 首页返回订单数量
     */
    @Override
    public Long getOrderTotal() {
        return orderMapper.countByExample(new OrderExample());
    }

    /**
     * 统计报表之订单统计
     * @return
     */
    @Override
    public StatBaseVO getOrderStat() {
        StatBaseVO statOrderVO = new StatBaseVO();
        ArrayList<String> columns = new ArrayList<>();
        columns.add("day");
        columns.add("orders");
        columns.add("customers");
        columns.add("amount");
        columns.add("pcr");
        statOrderVO.setColumns(columns);

        List<OrderStat> orderStats = orderMapper.selectGroupByAddTime();
        statOrderVO.setRows(orderStats);
        return statOrderVO;
    }
}

package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Order;
import com.cskaoyan.mall.bean.OrderExample;
import com.cskaoyan.mall.mapper.OrderMapper;
import com.cskaoyan.mall.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
/*
* 显示所有订单详情
* */
    @Override
    public Map<String, Object> queryOrderPageList(Integer page, Integer limit, String sort, String order,
                                                  List<Integer>orderStatusArray,Integer userId,Integer orderSn) {
        Map<String,Object> map= new HashMap< > ();
        PageHelper.startPage (page,limit);
        List<Order> orders=orderMapper.queryOrderPageList(orderStatusArray,userId,orderSn,sort,order);
        PageInfo pageInfo = new PageInfo (orders);
        long total =pageInfo.getTotal ();
        map.put ("total",total);
        map.put ("items",orders);
        return map;
    }
}

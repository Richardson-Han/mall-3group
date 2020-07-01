package com.cskaoyan.mall.service.impl;


import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.VO.OrderRefundVO;
import com.cskaoyan.mall.bean.VO.OrderStatusVO;
import com.cskaoyan.mall.bean.VO.ShipVO;
import com.cskaoyan.mall.bean.VO.StatBaseVO;
import com.cskaoyan.mall.mapper.OrderGoodsMapper;
import com.cskaoyan.mall.mapper.OrderMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/***
 * @author 社会主义好
 * @date 2020-06-26 17:21 星期五 
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    /**
     *
     * @return 首页返回订单数量
     */
    @Override
    public Long getOrderTotal() {
        return orderMapper.countByExample(new OrderExample ());
    }

/*
* 显示所有订单详情
* */
    @Override
    public Map<String, Object> queryOrderPageList(Integer page, Integer limit, String sort, String order,
                                                  List<Integer> orderStatusArray, Integer userId, Integer orderSn) {
        Map<String, Object> map = new HashMap<> ();
        PageHelper.startPage (page, limit);
        List<Order> orders = orderMapper.queryOrderPageList (orderStatusArray, userId, orderSn, sort, order);
        PageInfo pageInfo = new PageInfo (orders);
        long total = pageInfo.getTotal ();
        map.put ("total", total);
        map.put ("items", orders);
        return map;
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
        return  statOrderVO;
    }

    @Override
    public Map<String, Object> queryOrderDetailById(Integer id) {
        Map<String ,Object> map =new HashMap<> ();
        //查询订单表
        Order order = orderMapper.selectByPrimaryKey (id);
        //查询用户表
        User user=userMapper.selectByPrimaryKey (order.getUserId ());
        //查寻cskaoyanmall_order_goods表
        OrderGoods orderGoods=orderGoodsMapper.selectByPrimaryKey (id);
        map.put ("order",order);
        map.put("user",user);
        map.put ("orderGoods",orderGoods);
        return map;
    }

    @Override
    public void orderRefund(OrderRefundVO orderRefundVO) {
        Order order = orderMapper.selectByPrimaryKey(orderRefundVO.getOrderId());
        order.setUpdateTime(new Date ());
        order.setOrderStatus(OrderStatusVO.user_cancelled);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void orderShip(ShipVO shipVO) {
        Order order = orderMapper.selectByPrimaryKey (shipVO.getOrderId ());
        order.setUpdateTime (new Date ());
        order.setOrderStatus (OrderStatusVO.order_delivered);
    }

    @Override
    public Integer wxselectUnrecvByUserId(Integer userId) {
        return orderMapper.selectUnrecvByUserId(userId);
    }

    @Override
    public Integer wxselectUncommentByUserId(Integer userId) {
        return orderMapper.selectUncommentByUserId(userId);
    }

    @Override
    public Integer wxselectUnpaidByUserId(Integer userId) {
        return orderMapper.selectUnpaidByUserId(userId);
    }

    @Override
    public Integer wxselectUnshipByUserId(Integer userId) {
        return orderMapper.selectUnshipByUserId(userId);
    }
}

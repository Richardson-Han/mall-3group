package com.cskaoyan.mall.service.impl;


import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.VO.OrderRefundVO;
import com.cskaoyan.mall.bean.VO.OrderStatusVO;
import com.cskaoyan.mall.bean.VO.ShipVO;
import com.cskaoyan.mall.bean.VO.StatBaseVO;
import com.cskaoyan.mall.bean.wx.HandleOption;
import com.cskaoyan.mall.bean.wx.VO.WXOrderInfoVO;
import com.cskaoyan.mall.bean.wx.WXOrderGoods;
import com.cskaoyan.mall.mapper.OrderGoodsMapper;
import com.cskaoyan.mall.mapper.OrderMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.utils.WxHandleOptionUtil;
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
    public Integer queryGoodsIdByOrderId(Integer orderGoodsId) {
        Integer id = orderGoodsMapper.queryGoodsIdByOrderId(orderGoodsId);
        return id;
    }

    @Override
    public void updateCommentId(Integer orderGoodsId, Integer commentId) {
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setId(orderGoodsId);
        orderGoods.setComment(commentId);
        orderGoodsMapper.updateByPrimaryKeySelective(orderGoods);
    }

    @Override
    public List<Order> queryOrderByOrderStatus(Integer showType) {
        //判断订单状态
        if(showType == 0){
            List<Order> orders = orderMapper.selectAllOrders();
            return orders;
        }
        int status = 0;
        switch (showType){
            case 1 :
                status = 101;
                break;
            case 2 :
                status = 201;
                break;
            case 3 :
                status = 301;
                break;
            case 4 :
                status = 401;
                break;
        }
        List<Order> orders =  orderMapper.selectByOrderStatus(status);
        return orders;
    }

    @Override
    public List<WXOrderGoods> queryOrderGoodsByOrderId(Integer id) {
        List<WXOrderGoods> WXOrderGoodsList = orderGoodsMapper.selectByOrderId(id);
        return WXOrderGoodsList;
    }

    @Override
    public List<OrderGoods> selectOrderGoodsByOrderId(Integer orderId) {
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        orderGoodsExample.createCriteria().andOrderIdEqualTo(orderId);
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        return orderGoods;
    }

    @Override
    public WXOrderInfoVO getWxOrderInfo(Integer orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        WXOrderInfoVO wxOrderInfoVO = new WXOrderInfoVO();
        wxOrderInfoVO.setActualPrice(order.getActualPrice());
        wxOrderInfoVO.setAddTime(order.getAddTime());
        wxOrderInfoVO.setAddress(order.getAddress());
        wxOrderInfoVO.setConsignee(order.getConsignee());
        wxOrderInfoVO.setCouponPrice(order.getCouponPrice());
        wxOrderInfoVO.setFreightPrice(order.getFreightPrice());
        wxOrderInfoVO.setGoodsPrice(order.getGoodsPrice());
        HandleOption handleOption = WxHandleOptionUtil.getHandleOption(order.getOrderStatus());
        wxOrderInfoVO.setHandleOption(handleOption);
        wxOrderInfoVO.setId(order.getId());
        wxOrderInfoVO.setMobile(order.getMobile());
        wxOrderInfoVO.setOrderSn(order.getOrderSn());
        wxOrderInfoVO.setOrderStatusText(order.getOrderStatus());
        return wxOrderInfoVO;
    }

    @Override
    public void cancelOrder(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setOrderStatus((short) 102);
        order.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setUpdateTime(new Date());
        order.setDeleted(true);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void refund(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setUpdateTime(new Date ());
        order.setOrderStatus(OrderStatusVO.request_refund);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void confirmOrder(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setUpdateTime(new Date ());
        order.setOrderStatus(OrderStatusVO.user_receipt);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public OrderGoods getOrderGoods(Integer orderId, Integer goodsId) {
        OrderGoodsExample example = new OrderGoodsExample();
        example.createCriteria().andOrderIdEqualTo(orderId).andGoodsIdEqualTo(goodsId);
        List<OrderGoods> orderGoodsList = orderGoodsMapper.selectByExample(example);
        //只有一条数据
        return orderGoodsList.get(0);
    }
}

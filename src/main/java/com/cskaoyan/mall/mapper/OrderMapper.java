package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Order;
import com.cskaoyan.mall.bean.OrderExample;

import com.sun.javafx.image.IntPixelGetter;

import com.cskaoyan.mall.bean.OrderStat;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);


    List<Order> queryOrderPageList(List<Integer>orderStatusArray, Integer userId, Integer orderSn, String sort1, String order1);

    List<OrderStat> selectGroupByAddTime();

    List<Order> selectByOrderStatus(@Param("status") Integer status);

    List<Order> selectAllOrders();
}
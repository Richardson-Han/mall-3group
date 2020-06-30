package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Order;
import com.cskaoyan.mall.bean.OrderExample;

import com.cskaoyan.mall.bean.wx.WXOrderState;
import com.sun.javafx.image.IntPixelGetter;

import com.cskaoyan.mall.bean.OrderStat;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("select COUNT(*) as unrecv from cskaoyanmall_order where order_status=301 and user_id = #{userId};")
    Integer selectUnrecvByUserId(@Param("userId") Integer userId);

    @Select("select SUM(comments) as uncomment from cskaoyanmall_order where " +
            "(order_status=401 or order_status=402) and user_id = #{id}")
    Integer selectUncommentByUserId(@Param("id") Integer id);

    @Select("select COUNT(*) as unpaid from cskaoyanmall_order where order_status=101 and user_id = #{id}")
    Integer selectUnpaidByUserId(@Param("id") Integer id);

    @Select("select COUNT(*) as unship from cskaoyanmall_order where order_status=201 and user_id = #{id}")
    Integer selectUnshipByUserId(Integer id);
}
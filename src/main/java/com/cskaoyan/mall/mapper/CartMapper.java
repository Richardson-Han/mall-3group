package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Cart;
import com.cskaoyan.mall.bean.CartExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CartMapper {
    long countByExample(CartExample example);

    int deleteByExample(CartExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    List<Cart> selectByExample(CartExample example);

    Cart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByExample(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    @Update("update cskaoyanmall_cart set checked = #{checked} where product_id = #{productId} and user_id = #{userId}")
    void updateCheckedByIdAndUserId(@Param("productId")Integer productId, @Param("checked") Integer checked, @Param("userId") Integer userId);

    @Update("update cskaoyanmall_cart set number = #{number} where id = #{id}")
    void updateNumberById(@Param("id")Integer id, @Param("number") Integer number);

    @Update("update cskaoyanmall_cart set deleted = 1 where user_id = #{userId} and product_id = #{productId}")
    void updateDeletedByProductIdAndUserId(@Param("userId")Integer userId, @Param("productId") Integer productId);

}
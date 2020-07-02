package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.wx.BO.CartCheckBO;
import com.cskaoyan.mall.bean.wx.BO.CartCheckoutBO;
import com.cskaoyan.mall.bean.wx.BO.CartUpdateBO;
import com.cskaoyan.mall.bean.wx.VO.CartCheckoutVO;
import com.cskaoyan.mall.bean.wx.VO.CartListVO;

import java.util.List;
import java.util.Map;

/**
 * @author 方
 * @create 2020/7/1-16:40
 */
public interface CartService {

    CartListVO queryListByUsername(String username);

    CartListVO checked(CartCheckBO cartCheckBO, String username);

    void update(CartUpdateBO cartUpdateBO);

    CartListVO delete(List<Integer> productIds, String username);

    CartCheckoutVO checkout(CartCheckoutBO checkoutBO, String username);

    Integer goodscount(String username);

    Integer add(Map map, String username);

    Integer fastadd(Map map, String username);

    //CartListVO checked(WxCartCheckedPostVO checkedMap);

    //void update(Map<String, Object> updateMap);

    //CartListVO delete(Map<String , List<Integer>> deleteList);

}

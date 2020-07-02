package com.cskaoyan.mall.bean.wx.VO;

import com.cskaoyan.mall.bean.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Fang
 * @create 2020/7/1-16:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartListVO {
    List<Cart> cartList;
    CartTotalVO cartTotal;
}
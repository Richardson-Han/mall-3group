package com.cskaoyan.mall.bean.wx.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Fang
 * @create 2020/7/2-0:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartCheckoutBO {
    Integer cartId;
    Integer addressId;
    Integer couponId;
    Integer grouponRulesId;
}

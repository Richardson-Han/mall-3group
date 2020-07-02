package com.cskaoyan.mall.bean.wx.VO;

import com.cskaoyan.mall.bean.Address;
import com.cskaoyan.mall.bean.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Fang
 * @create 2020/7/1-23:31
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartCheckoutVO {
    Double actualPrice;

    Integer addressId;

    Integer availableCouponLength;

    Address checkedAddress;

    List<Cart> checkedGoodsList;

    Integer couponId;

    Double couponPrice;

    Integer freightPrice;

    Double goodsTotalPrice;

    Double grouponPrice;

    Integer grouponRulesId;

    Double orderTotalPrice;

}

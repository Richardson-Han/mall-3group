package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Coupon;

/**
 * @create 2020-06-27 0:26
 */
public interface CouponService {
    BaseData queryCoupon(Integer page, Integer limit, String sort, String order);

    Coupon readCoupon(Integer id);


    Integer createCoupon(Coupon coupon);

    Integer updateCoupon(Coupon coupon);

    Integer deleteCoupon(Coupon coupon);

    BaseData listuserUserIdCouponUser(Integer page, Integer limit, Integer couponId, Integer userId, String sort, String order);

    BaseData listuserCouponUser(Integer page, Integer limit, Integer couponId, String sort, String order);

}

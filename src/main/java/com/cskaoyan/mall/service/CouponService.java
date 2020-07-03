package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Coupon;
import com.cskaoyan.mall.bean.wx.CouponBase;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 韩
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

    CouponBase WXlistCoupon(Integer page, Integer size);

    Integer wxExchangecouponId(String code);

    Integer wxExchangeLimit(Integer couponId);

    Integer wxExchangecountByUserId(Integer userId,Integer couponId);

    Integer wxselectTolalNumberByCouponId(Integer couponId);

    CouponBase wxselectCouponByStatusPage(Integer status, Integer page, Integer size, Integer userId);

    List<Coupon> wxselectCouponByCartId(Integer cartId, Integer grouponRulesId, Integer userId);

    List<Coupon> wxselectNewCoupons();

    Integer selectLastId();
}

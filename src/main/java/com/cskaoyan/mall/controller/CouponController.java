package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.Coupon;
import com.cskaoyan.mall.service.CouponService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 韩
 * @create 2020-06-27 0:23
 */
@RestController
@RequestMapping("admin/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseRespVo list(Integer page, Integer limit, String sort, String order) {
        BaseData baseData = couponService.queryCoupon(page, limit, sort, order);
        return BaseRespVo.ok(baseData);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public BaseRespVo create(@RequestBody Coupon coupon) {
        Integer insert = couponService.createCoupon(coupon);
        if (insert == 1) {
            coupon.setGoodsValue("[]");
            coupon.setId(couponService.selectLastId());
            return BaseRespVo.ok(coupon);
        } else {
            return BaseRespVo.error("创建优惠卷失败", 888);
        }
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public BaseRespVo read(Integer id) {
        Coupon coupon = couponService.readCoupon(id);
        return BaseRespVo.ok(coupon);
    }

    @RequestMapping(value = "listuser", method = RequestMethod.GET)
    public BaseRespVo listuser(Integer page, Integer limit, Integer couponId,
                               Integer userId, String sort, String order) {
        BaseData baseData;
        if (userId == null || userId == 0) {
            baseData = couponService.listuserCouponUser(page, limit, couponId, sort, order);
        } else {
            baseData = couponService.listuserUserIdCouponUser(page, limit, couponId, userId, sort, order);
        }
        return BaseRespVo.ok(baseData);
    }

    /**
     * 更新优惠卷信息
     * 注：页面已发送最新updatetime 不需要再手动设置
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public BaseRespVo update(@RequestBody Coupon coupon) {
        Integer updateCoupon = couponService.updateCoupon(coupon);
        if (updateCoupon == 1) {
            return BaseRespVo.ok();
        } else {
            return BaseRespVo.error("数据更新失败", 112);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseRespVo delete(@RequestBody Coupon coupon) {
        Integer deleteCoupon = couponService.deleteCoupon(coupon);
        if (deleteCoupon == 1) {
            return BaseRespVo.ok();
        } else {
            return BaseRespVo.error("删除失败", 404);
        }
    }

}

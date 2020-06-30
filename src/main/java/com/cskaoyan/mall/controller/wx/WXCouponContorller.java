package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Coupon;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 韩
 * @create 2020-06-30 2:33
 */
@RestController
@RequestMapping("/wx/coupon")
public class WXCouponContorller {

    @Autowired
    CouponService couponService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseRespVo list(Integer page, Integer limit, String sort, String order) {
        BaseData baseData = couponService.queryCoupon(page, limit, sort, order);
        return BaseRespVo.ok(baseData);
    }

    @RequestMapping("mylist")
    public BaseRespVo mylist() {
        return BaseRespVo.ok();
    }

    @RequestMapping("selectlist")
    public BaseRespVo selectlist() {
        return BaseRespVo.ok();
    }

    @RequestMapping("receive")
    public BaseRespVo receive() {
        return BaseRespVo.ok();
    }

    @RequestMapping("exchange")
    public BaseRespVo exchange() {
        return BaseRespVo.ok();
    }

}

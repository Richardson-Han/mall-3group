package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Coupon;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.wx.CouponBase;
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

    /**
     * 优惠卷专区
     *
     * @param page 第几页
     * @param size 一页几张
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseRespVo list(Integer page, Integer size) {
        //传入的page值大了1 页码以1为起始，sql以0为起始，故而--
        page--;
        CouponBase baseData = couponService.WXlistCoupon(page, size);
        return BaseRespVo.ok(baseData);
    }

    /**
     * {"errno":0,"data":{"data":[{"id":8,"name":"可兑换优惠券","desc":"全场通用","tag":"仅兑换领券",
     *     "min":"99.00","discount":"15.00","startTime":"2020-06-30 11:08:03","endTime":"2020-07-07 11:08:03"}],
     *     "count":13},"errmsg":"成功"}
     *     status = 0 未使用  status = 1 已使用 status = 2 已过期
     */
    /**
     * 我的优惠卷
     */
    @RequestMapping("mylist")
    public BaseRespVo mylist(Integer status, Integer page, Integer size) {
        return BaseRespVo.ok();
    }

    /**
     * 下单界面选择优惠卷
     * {"errno":0,"data":[{"id":21,"name":"2333","desc":"hello","tag":"tag","min":"20.00",
     * "discount":"1.00","startTime":"2020-06-30 23:10:39","endTime":"2020-07-10 23:10:39"}],"errmsg":"成功"}
     * <p>
     * {"errno":0,"data":[{"id":21,"name":"2333","desc":"hello","tag":"tag","min":"20.00",
     * "discount":"1.00","startTime":"2020-06-30 23:10:39","endTime":"2020-07-10 23:10:39"},
     * {"id":8,"name":"可兑换优惠券","desc":"全场通用","tag":"仅兑换领券","min":"99.00",
     * "discount":"15.00","startTime":"2020-06-30 11:08:03","endTime":"2020-07-07 11:08:03"}],"errmsg":"成功"}
     */
    @RequestMapping("selectlist")
    public BaseRespVo selectlist(Integer cartId, Integer grouponRulesId) {
        return BaseRespVo.ok();
    }

    /**
     * 领卷功能
     * {"errno":740,"errmsg":"优惠券已经领取过"}
     * {"errno":0,"errmsg":"成功"}
     * {"errno":740,"errmsg":"优惠券已领完"}
     * couponId → 卷ID
     * 通过token获取用户ID 将卷ID塞入库
     */
    @RequestMapping("receive")
    public BaseRespVo receive(Integer couponId) {
        return BaseRespVo.ok();
    }

    @RequestMapping("exchange")
    public BaseRespVo exchange() {
        return BaseRespVo.ok();
    }

}

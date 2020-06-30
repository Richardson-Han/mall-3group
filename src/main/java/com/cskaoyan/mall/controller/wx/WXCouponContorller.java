package com.cskaoyan.mall.controller.wx;


import com.cskaoyan.mall.bean.Coupon;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.wx.CouponBase;
import com.cskaoyan.mall.service.CouponService;
import com.cskaoyan.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author 韩
 * @create 2020-06-30 2:33
 */
@RestController
@RequestMapping("/wx/coupon")
public class WXCouponContorller {

    @Autowired
    CouponService couponService;

    @Autowired
    UserService userService;

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
     * 我的优惠卷
     */
    @RequestMapping("mylist")
    public BaseRespVo mylist(Integer status, Integer page, Integer size,HttpServletRequest request) {
        //后续要封装从request →username
        //先获取用户
        HttpSession session = request.getSession();
        //获取token
        String token = request.getHeader("X-cskaoyan-mall-Admin-Token");
        if ("j65dcjj0if0tf223567uwgu9a7t7b1z8".equals(token)){
            String username = "test1";
            Integer userId = userService.wxselectIdByUsername(username);
            CouponBase couponBase = couponService.wxselectCouponByStatusPage(status,--page,size,userId);
            return BaseRespVo.ok(couponBase);
        }else {
            return BaseRespVo.error("请先登陆");
        }
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
    public BaseRespVo selectlist(@RequestBody Map map,HttpServletRequest request) {
        Integer cartId = (Integer) map.get("cartId");
        Integer grouponRulesId = (Integer) map.get("grouponRulesId");
        //先获取用户
        HttpSession session = request.getSession();
        //获取token
        String token = request.getHeader("X-cskaoyan-mall-Admin-Token");
        if ("j65dcjj0if0tf223567uwgu9a7t7b1z8".equals(token)){
            String username = "test1";
            Integer userId = userService.wxselectIdByUsername(username);
            //还有三条SQL语句没写 仅能在cartId=0，grouponRulesId=0状态下使用
            List<Coupon> couponBase = couponService.wxselectCouponByCartId(cartId,grouponRulesId,userId);
            return BaseRespVo.ok(couponBase);
        }else {
            return BaseRespVo.error("请先登陆");
        }
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
    public BaseRespVo receive(@RequestBody Map map, HttpServletRequest request) {
        Integer couponId = (Integer) map.get("couponId");
        //先获取用户
        HttpSession session = request.getSession();
        //获取token
        String token = request.getHeader("X-cskaoyan-mall-Admin-Token");
        return getCouponFunction(token,couponId);
    }

    /**
     * 领卷码领取优惠卷
     * 三种返回值
     * {"errno":742,"errmsg":"优惠券不正确"}
     * {"errno":740,"errmsg":"优惠券已兑换"}
     * {"errno":0,"errmsg":"成功"}
     */
    @RequestMapping("exchange")
    public BaseRespVo exchange(@RequestBody Map map , HttpServletRequest request) {
        String code = (String) map.get("code");
        System.out.println(code);
        //通过code查到对应优惠卷 id
        Integer couponId = couponService.wxExchangecouponId(code);
        System.out.println(couponId);
        //查不到对应优惠卷返回错误
        if (couponId == null || couponId == 0) {
            return BaseRespVo.error("优惠券不正确", 742);
        }
        //先获取用户
        HttpSession session = request.getSession();
        //获取token
        String token = request.getHeader("X-cskaoyan-mall-Admin-Token");
        return getCouponFunction(token,couponId);
    }

    /**
     * 获取优惠卷工具方法
     */
    private BaseRespVo getCouponFunction(String token,Integer couponId){
        //查看优惠卷剩余可领取次数
        Integer total = couponService.wxselectTolalNumberByCouponId(couponId);
        //total=null意味着优惠卷最后一张已经被领取 随之被虚拟删除标记
        if (total == null || total == -1) {
            return BaseRespVo.error("优惠券已领完", 740);
        }
        //查看优惠卷同一用户可领取次数
        Integer limitnumber = couponService.wxExchangeLimit(couponId);
        //如果无限次的直接领卷并返回成功！
        if (limitnumber == 0) {
            //shiro 未完成 无法获得其他token值比较获取真正当前登陆用户
            if ("j65dcjj0if0tf223567uwgu9a7t7b1z8".equals(token)) {
                String username = "test1";
                Integer userId = userService.wxselectIdByUsername(username);
                //领卷并更新优惠卷剩余数目
                Integer insert = userService.wxinsertGainCoupon(userId, couponId,total);
                return insert == 0 ? BaseRespVo.error("领取失败,请重试", 711) : BaseRespVo.ok();

            } else {
                return BaseRespVo.error("请先登陆");
            }
        }
        //如果限次 查看用户是否领取过 次数是否还有
        //shiro 未完成 无法获得其他token值比较获取真正当前登陆用户
        if ("j65dcjj0if0tf223567uwgu9a7t7b1z8".equals(token)) {
            String username = "test1";
            //username得到user_id
            Integer userId = userService.wxselectIdByUsername(username);
            Integer count = couponService.wxExchangecountByUserId(userId, couponId);
            if (count < limitnumber) {
                //领卷并更新优惠卷剩余数目
                Integer insert = userService.wxinsertGainCoupon(userId, couponId, total);
                return insert == 0 ? BaseRespVo.error("领取失败,请重试", 711) : BaseRespVo.ok();
            } else {
                return BaseRespVo.error("优惠券不正确", 740);
            }
        } else {
            return BaseRespVo.error("请先登陆");
        }
    }
}

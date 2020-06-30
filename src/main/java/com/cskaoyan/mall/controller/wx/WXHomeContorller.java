package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.wx.FloorGoods;
import com.cskaoyan.mall.bean.wx.GroupBuy;
import com.cskaoyan.mall.bean.wx.WXUser;
import com.cskaoyan.mall.mapper.*;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.System;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 韩
 * @create 2020-06-30 2:32
 */
@RestController
@RequestMapping(value = "/wx/home")
public class WXHomeContorller {

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    GroupOnRulesMapper groupOnRulesMapper;

    @Autowired
    AdvertisingMapper advertisingMapper;

    @Autowired
    BrandMapper brandMapper;

    @Autowired
    TopicMapper topicMapper;

    /**
     * 存在问题 无法访问
     * 1.RequiresAuthentication:  该注解标注的类，实例，方法在访问或调用时，
     * 当前Subject必须在当前session中已经过认证
     * 2.RequiresGuest: 用于来宾任意访问
     * 3.RequiresPermissions:  当前Subject需要拥有某些特定的权限时，才能执行被该注解标注的方法。
     * 如果当前Subject不具有这样的权限，则方法不会被执行
     * 4.RequiresRoles:  当前Subject必须拥有所有指定的角色时，才能访问被该注解标注的方法。
     * 如果当前Subject不同时拥有所有指定角色，则方法不会执行还会抛出AuthorizationException异常。
     * 5.RequiresUser:  当前Subject必须是应用的用户，才能访问或调用被该注解标注的类，实例，方法。
     */
    @RequiresGuest
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public BaseRespVo index(@RequestBody Map map) {
        String token = (String) map.get("X-cskaoyan-mall-Admin-Token");
        List<Goods> newGoodsList = goodsMapper.selectNewgoods();
        List<Coupon> couponList = couponMapper.selectNewCoupons();
        List<Category> channel = categoryMapper.selectLimitTen();
        List<GroupBuy> grouponList = groupOnRulesMapper.selectGroupBuy();
        List<Advertising> banner = advertisingMapper.selectTopAdvertising();
        List<Brand> brandList = brandMapper.selectDirectSupply();
        List<Goods> hotGoodsList = goodsMapper.selectHotGoods();
        List<Topic> topicList = topicMapper.selectNewTopic();
        //4个品类各4个商品
        List<FloorGoods> listFloorGoodss = goodsMapper.selectCategoryFour();
        System.out.println(listFloorGoodss);
        List<FloorGoods> floorGoodsList = new ArrayList<>();
        for (FloorGoods floorGoods : listFloorGoodss) {
            floorGoods.setGoodsList(goodsMapper.selectByCategoryid(floorGoods.getId()));
            floorGoodsList.add(floorGoods);
        }
        WXUser data = new WXUser(newGoodsList, couponList, channel, grouponList, banner, brandList, hotGoodsList, topicList, floorGoodsList);
        return BaseRespVo.ok(data);
    }
}

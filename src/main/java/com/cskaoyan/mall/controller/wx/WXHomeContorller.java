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
@RequiresGuest
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

    @RequiresGuest
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public BaseRespVo index() {
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
        List<FloorGoods> floorGoodsList = new ArrayList<>();
        for (FloorGoods floorGoods : listFloorGoodss) {
            floorGoods.setGoodsList(goodsMapper.selectByCategoryid(floorGoods.getId()));
            floorGoodsList.add(floorGoods);
        }
        WXUser data = new WXUser(newGoodsList, couponList, channel, grouponList, banner, brandList, hotGoodsList, topicList, floorGoodsList);
        return BaseRespVo.ok(data);
    }
}

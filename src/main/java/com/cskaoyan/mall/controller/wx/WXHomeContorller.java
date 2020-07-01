package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.wx.WXFloorGoods;
import com.cskaoyan.mall.bean.wx.WXGroupBuy;
import com.cskaoyan.mall.bean.wx.WXUser;
import com.cskaoyan.mall.mapper.*;
import com.cskaoyan.mall.service.*;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 韩
 * @create 2020-06-30 2:32
 */
@RestController
@RequestMapping(value = "/wx/home")
public class WXHomeContorller {

    @Autowired
    GoodsService goodsService;

    @Autowired
    CouponService couponService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    GroupService groupService;

    @Autowired
    AdvertisingService advertisingService;

    @Autowired
    BrandService brandService;

    @Autowired
    TopicService topicService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public BaseRespVo index() {
        List<Goods> newGoodsList = goodsService.wxselectNewgoods();
        List<Coupon> couponList = couponService.wxselectNewCoupons();
        List<Category> channel = categoryService.wxselectLimitTen();
        List<WXGroupBuy> grouponList = groupService.wxselectGroupBuy();
        List<Advertising> banner = advertisingService.wxselectTopAdvertising();
        List<Brand> brandList = brandService.wxselectDirectSupply();
        List<Goods> hotGoodsList = goodsService.wxselectHotGoods();
        List<Topic> topicList = topicService.wxselectNewTopic();
        //4个品类各4个商品
        List<WXFloorGoods> listWXFloorGoodsses = goodsService.wxselectCategoryFour();
        List<WXFloorGoods> WXFloorGoodsList = new ArrayList<>();
        for (WXFloorGoods WXFloorGoods : listWXFloorGoodsses) {
            WXFloorGoods.setGoodsList(goodsService.wxselectByCategoryid(WXFloorGoods.getId()));
            WXFloorGoodsList.add(WXFloorGoods);
        }
        WXUser data = new WXUser(newGoodsList, couponList, channel, grouponList, banner, brandList, hotGoodsList,
                topicList, WXFloorGoodsList);
        return BaseRespVo.ok(data);
    }
}

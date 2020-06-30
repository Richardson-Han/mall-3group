package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.wx.WXFloorGoods;
import com.cskaoyan.mall.bean.wx.WXGroupBuy;
import com.cskaoyan.mall.bean.wx.WXUser;
import com.cskaoyan.mall.mapper.*;
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
        List<WXGroupBuy> grouponList = groupOnRulesMapper.selectGroupBuy();
        List<Advertising> banner = advertisingMapper.selectTopAdvertising();
        List<Brand> brandList = brandMapper.selectDirectSupply();
        List<Goods> hotGoodsList = goodsMapper.selectHotGoods();
        List<Topic> topicList = topicMapper.selectNewTopic();
        //4个品类各4个商品
        List<WXFloorGoods> listWXFloorGoodsses = goodsMapper.selectCategoryFour();
        List<WXFloorGoods> WXFloorGoodsList = new ArrayList<>();
        for (WXFloorGoods WXFloorGoods : listWXFloorGoodsses) {
            WXFloorGoods.setGoodsList(goodsMapper.selectByCategoryid(WXFloorGoods.getId()));
            WXFloorGoodsList.add(WXFloorGoods);
        }
        WXUser data = new WXUser(newGoodsList, couponList, channel, grouponList, banner, brandList, hotGoodsList, topicList, WXFloorGoodsList);
        return BaseRespVo.ok(data);
    }
}

package com.cskaoyan.mall;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.wx.FloorGoods;
import com.cskaoyan.mall.bean.wx.GroupBuy;
import com.cskaoyan.mall.bean.wx.WXUser;
import com.cskaoyan.mall.mapper.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.System;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 韩
 * @create 2020-06-30 5:34
 */
@SpringBootTest
public class HANWXSQLTest {
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

    @Test
    public void hantest1() {
        List<FloorGoods> listFloorGoodss = goodsMapper.selectCategoryFour();
        System.out.println(listFloorGoodss);
        List<FloorGoods> floorGoodsList = new ArrayList<>();
        for (FloorGoods floorGoods : listFloorGoodss) {
            floorGoods.setGoodsList(goodsMapper.selectByCategoryid(floorGoods.getId()));
            floorGoodsList.add(floorGoods);
        }
        for (FloorGoods floorGoods : floorGoodsList) {
            System.out.println("~~~~~~~~~~~~~~~~~~");
            System.out.print("id = " + floorGoods.getId());
            System.out.print(",name = " + floorGoods.getName());
            for (Goods goods : floorGoods.getGoodsList()) {
                System.out.println(",goods = " + goods);
            }
        }
    }
    @Test
    public void HanTest2(){
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
        WXUser data = new WXUser(newGoodsList,couponList,channel,grouponList,
                banner,brandList,hotGoodsList,topicList,floorGoodsList);
        System.out.println("data");
    }

}

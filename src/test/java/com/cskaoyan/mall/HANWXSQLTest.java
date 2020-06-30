package com.cskaoyan.mall;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.wx.WXFloorGoods;
import com.cskaoyan.mall.bean.wx.WXGroupBuy;
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

    @Autowired
    OrderMapper orderMapper;

    @Test
    public void hantest1() {
        List<WXFloorGoods> listWXFloorGoodsses = goodsMapper.selectCategoryFour();
        System.out.println(listWXFloorGoodsses);
        List<WXFloorGoods> WXFloorGoodsList = new ArrayList<>();
        for (WXFloorGoods WXFloorGoods : listWXFloorGoodsses) {
            WXFloorGoods.setGoodsList(goodsMapper.selectByCategoryid(WXFloorGoods.getId()));
            WXFloorGoodsList.add(WXFloorGoods);
        }
        for (WXFloorGoods WXFloorGoods : WXFloorGoodsList) {
            System.out.println("~~~~~~~~~~~~~~~~~~");
            System.out.print("id = " + WXFloorGoods.getId());
            System.out.print(",name = " + WXFloorGoods.getName());
            for (Goods goods : WXFloorGoods.getGoodsList()) {
                System.out.println(",goods = " + goods);
            }
        }
    }

    @Test
    public void HanTest2() {
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
        System.out.println(listWXFloorGoodsses);
        List<WXFloorGoods> WXFloorGoodsList = new ArrayList<>();
        for (WXFloorGoods WXFloorGoods : listWXFloorGoodsses) {
            WXFloorGoods.setGoodsList(goodsMapper.selectByCategoryid(WXFloorGoods.getId()));
            WXFloorGoodsList.add(WXFloorGoods);
        }
        WXUser data = new WXUser(newGoodsList, couponList, channel, grouponList,
                banner, brandList, hotGoodsList, topicList, WXFloorGoodsList);
        System.out.println("data");
    }

    /**
     * 验证语句有无错误
     */
    @Test
    public void HanTest3() {
        Integer integer = orderMapper.selectUnrecvByUserId(4);
        System.out.println("Unrecv = " + integer);
        integer = orderMapper.selectUnpaidByUserId(2);
        System.out.println("Unpaid = " + integer);
        integer = orderMapper.selectUnshipByUserId(1);
        System.out.println("Unship = " + integer);

        integer = orderMapper.selectUncommentByUserId(3);
        integer = integer == null ? 0 : integer;
        System.out.println("Uncomment = " + integer);
    }

}

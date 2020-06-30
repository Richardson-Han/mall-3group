package com.cskaoyan.mall.bean.wx;

import com.cskaoyan.mall.bean.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 韩
 * @create 2020-06-30 6:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WXUser {
    List<Goods> newGoodsList;
    List<Coupon> couponList;
    List<Category> channel;
    List<WXGroupBuy> grouponList;
    List<Advertising> banner;
    List<Brand> brandList;
    List<Goods> hotGoodsList;
    List<Topic> topicList;
    List<WXFloorGoods> WXFloorGoodsList;
}

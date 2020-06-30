package com.cskaoyan.mall.bean.wx;

import com.cskaoyan.mall.bean.Goods;
import lombok.Data;

import java.util.List;

/**
 * @author 韩
 * @create 2020-06-30 5:26
 */
@Data
public class WXFloorGoods {
    String name;
    List<Goods> goodsList;
    Integer id;
}

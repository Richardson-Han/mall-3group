package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;

import java.util.List;

public interface GoodsService {
    Long getGoodsTotal();

    Long getProductTotal();

    BaseData queryGoods(GoodsListBO goodsListBO);

    GoodsDetailVO queryGoods(Integer id);

    GoodsCatAndBrandVO queryCatAndBrand();

    int updateGoods(GoodsUpdateBO goodsUpdateBO);

    int createGoods(GoodsUpdateBO goodsUpdateBO);

    void goodsDelete(Goods goods);
}

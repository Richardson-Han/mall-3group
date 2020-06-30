package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BO.GoodsListBO;
import com.cskaoyan.mall.bean.BO.GoodsUpdateBO;
import com.cskaoyan.mall.bean.VO.GoodsCatAndBrandVO;
import com.cskaoyan.mall.bean.VO.GoodsDetailVO;
import com.cskaoyan.mall.bean.VO.StatBaseVO;
import com.cskaoyan.mall.bean.*;

import java.util.Map;

public interface GoodsService {
    Long getGoodsTotal();

    StatBaseVO getGoodsStat();

    Long getProductTotal();

    BaseData queryGoods(GoodsListBO goodsListBO);

    GoodsDetailVO queryGoods(Integer id);

    GoodsCatAndBrandVO queryCatAndBrand();

    int updateGoods(GoodsUpdateBO goodsUpdateBO);

    int createGoods(GoodsUpdateBO goodsUpdateBO);

    void goodsDelete(Goods goods);

    Map category(Integer id);

    Map list(Integer categoryId, Integer page, Integer size);
}

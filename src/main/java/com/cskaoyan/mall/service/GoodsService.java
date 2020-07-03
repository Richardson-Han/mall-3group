package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BO.GoodsListBO;
import com.cskaoyan.mall.bean.BO.GoodsUpdateBO;
import com.cskaoyan.mall.bean.VO.GoodsCatAndBrandVO;
import com.cskaoyan.mall.bean.VO.GoodsDetailVO;
import com.cskaoyan.mall.bean.VO.StatBaseVO;
import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.wx.WXFloorGoods;

import java.util.List;
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

    Map category(Integer pid);

    Map list(Integer categoryId, Integer page, Integer size, String keyword, String sort, String order, Integer brandId);

    Map detail(Integer goodsId);

    Map related(Integer id);

    List<Goods> wxselectNewgoods();

    List<Goods> wxselectHotGoods();

    List<WXFloorGoods> wxselectCategoryFour();

    List<Goods> wxselectByCategoryid(Integer id);

    Integer selectLastId();
}

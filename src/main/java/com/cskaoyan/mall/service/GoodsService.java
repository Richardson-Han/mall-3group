package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.VO.StatBaseVO;

public interface GoodsService {
    Long getGoodsTotal();

    StatBaseVO getGoodsStat();
}

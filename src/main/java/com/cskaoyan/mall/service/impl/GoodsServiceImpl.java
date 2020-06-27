package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.GoodsExample;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @author 社会主义好
 * @date 2020-06-26 17:23 星期五 
 *
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    /**
     *
     * @return 首页返回商品数量
     */
    @Override
    public Long getGoodsTotal() {
        return goodsMapper.countByExample(new GoodsExample());
    }
}

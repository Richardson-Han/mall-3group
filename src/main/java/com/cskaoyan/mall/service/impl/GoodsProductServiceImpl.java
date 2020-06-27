package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.GoodsProductExample;
import com.cskaoyan.mall.mapper.GoodsProductMapper;
import com.cskaoyan.mall.service.GoodsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @author 社会主义好
 * @date 2020-06-26 17:24 星期五 
 *
 */
@Service
public class GoodsProductServiceImpl implements GoodsProductService {

    @Autowired
    GoodsProductMapper goodsProductMapper;
    /**
     *
     * @return 首页返回货品数量
     */
    @Override
    public Long getProductTotal() {
        return goodsProductMapper.countByExample(new GoodsProductExample());
    }
}

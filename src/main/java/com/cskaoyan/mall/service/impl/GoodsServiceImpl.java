package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.GoodsExample;
import com.cskaoyan.mall.bean.GoodsStat;
import com.cskaoyan.mall.bean.OrderStat;
import com.cskaoyan.mall.bean.VO.StatBaseVO;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 统计报表之商品统计
     * @return
     */
    @Override
    public StatBaseVO getGoodsStat() {
        StatBaseVO statGoodsVO = new StatBaseVO();
        ArrayList<String> columns = new ArrayList<>();
        columns.add("day");
        columns.add("orders");
        columns.add("products");
        columns.add("amount");
        statGoodsVO.setColumns(columns);

        List<GoodsStat> orderStats = goodsMapper.selectGroupByAddTime();
        statGoodsVO.setRows(orderStats);
        return statGoodsVO;

    }
}

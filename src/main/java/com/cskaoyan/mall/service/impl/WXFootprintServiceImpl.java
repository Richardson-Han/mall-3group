package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Footprint;
import com.cskaoyan.mall.bean.FootprintExample;
import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.VO.wx.WXGoodsVO;
import com.cskaoyan.mall.mapper.FootprintMapper;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.service.WXFootprintService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/* *
@author  Walker-胡
@create 2020-06-30 19:28
*/

@Service
public class WXFootprintServiceImpl implements WXFootprintService {
    @Autowired
    FootprintMapper wxFootprintMapper;
    @Autowired
    GoodsMapper goodsMapper;

    /*
    * 查询浏览足迹
    *
    * */
    @Override
    public Map queryWXFootprintList(Integer page, Integer size, Integer userId) {
        Map<String,Object> map=new HashMap<> ();
        // FootprintExample footprintExample = new FootprintExample();
        // FootprintExample.Criteria criteria = footprintExample.createCriteria().andDeletedEqualTo (false);
        // footprintExample.setOrderByClause ("id"+" "+"desc" );
        List<Footprint> wxList =wxFootprintMapper.selectlistByPageAndSizeAndUserid(--page,size,userId);
        List<WXGoodsVO>  wxGoodsList=new ArrayList<> ();
        for (Footprint wxFootprint : wxList) {
            Goods good = goodsMapper.selectByPrimaryKey (wxFootprint.getGoodsId ());
            WXGoodsVO wxGoodsVO = new WXGoodsVO (good.getBrief (), good.getPicUrl (), good.getAddTime (), good.getId (),
                    good.getName (), wxFootprint.getId (), good.getRetailPrice ());
            wxGoodsList.add (wxGoodsVO);
        }
        PageInfo pageInfo = new PageInfo (wxGoodsList);
        long total = pageInfo.getTotal ();
        map.put ("totalPages",total);
        map.put ("footprintList",wxGoodsList);
        return map;

    }
/*
* 删除足迹，做的是假删除
* */
    @Override
    public boolean deleteWXFootprint(Integer id) {
        Footprint footprint = wxFootprintMapper.selectByPrimaryKey (id);
        if(footprint.getDeleted ()){
            return false;
        }
        footprint.setDeleted (true);
        footprint.setUpdateTime (new Date ());
        int i = wxFootprintMapper.updateByPrimaryKey (footprint);
        return i==1;
    }
}

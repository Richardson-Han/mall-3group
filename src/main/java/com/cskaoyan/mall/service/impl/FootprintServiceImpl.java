package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Footprint;
import com.cskaoyan.mall.bean.FootprintExample;
import com.cskaoyan.mall.mapper.FootprintMapper;
import com.cskaoyan.mall.service.FootprintService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @author 社会主义好
 * @date 2020-06-27 7:45 星期六 
 *
 */
@Service
public class FootprintServiceImpl implements FootprintService {



    @Autowired
    FootprintMapper footprintMapper;

    @Override
    public BaseData queryFootprint(Integer page, Integer limit, String sort, String order, Integer userId, Integer goodsId) {

        FootprintExample footprintExample = new FootprintExample();
        FootprintExample.Criteria criteria = footprintExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (goodsId != null) {
            criteria.andGoodsIdEqualTo(goodsId);
        }
        footprintExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        List<Footprint> footprints = footprintMapper.selectByExample(footprintExample);
        long total = new PageInfo<>(footprints).getTotal();
        return new BaseData(footprints, total);
    }

}

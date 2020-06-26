package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Advertising;
import com.cskaoyan.mall.bean.AdvertisingExample;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.mapper.AdvertisingMapper;
import com.cskaoyan.mall.service.AdvertisingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @create 2020-06-26 20:03
 */
@Service
public class AdvertisingServiceImpl implements AdvertisingService {

    @Autowired
    AdvertisingMapper advertisingMapper;

    @Override
    public BaseData queryAdvertising(Integer page, Integer limit, String sort, String order) {
        AdvertisingExample advertisingExample = new AdvertisingExample();
        advertisingExample.setOrderByClause(sort + " " + order);
        //执行查询之前使用分页
        PageHelper.startPage(page,limit);
        List<Advertising> advertisings = advertisingMapper.selectByExample(advertisingExample);
        PageInfo<Advertising> pageInfo = new PageInfo<>(advertisings);
        long total = pageInfo.getTotal();
        return new BaseData(advertisings, total);
    }

    @Override
    public Integer insertAdvertising(Advertising advertising) {
        int insert = advertisingMapper.insert(advertising);
        return insert;
    }
}

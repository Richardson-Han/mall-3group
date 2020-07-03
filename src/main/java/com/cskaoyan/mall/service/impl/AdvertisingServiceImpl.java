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
 * @author 韩
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
        PageHelper.startPage(page, limit);
        List<Advertising> advertisings = advertisingMapper.selectByExample(advertisingExample);
        PageInfo<Advertising> pageInfo = new PageInfo<>(advertisings);
        long total = pageInfo.getTotal();
        return new BaseData(advertisings, total);
    }

    /**
     * 新增广告信息
     */
    @Override
    public Integer insertAdvertising(Advertising advertising) {
        advertising.setAddTime(new Date());
        advertising.setUpdateTime(new Date());
        return advertisingMapper.insertSelective(advertising);
    }

    /**
     * 修改广告数据
     */
    @Override
    public Integer updateAdvertising(Advertising advertising) {
        return advertisingMapper.updateByPrimaryKeySelective(advertising);
    }

    /**
     * 虚拟删除 采用更新
     */
    @Override
    public Integer deleteAdvertising(Advertising advertising) {
        advertising.setUpdateTime(new Date());
        return advertisingMapper.updateByPrimaryKeySelective(advertising);
    }

    @Override
    public List<Advertising> wxselectTopAdvertising() {
        return advertisingMapper.selectTopAdvertising();
    }

    @Override
    public Advertising selectLastAdvertising() {
        Integer id = advertisingMapper.selectLastAdvertisingId();
        return advertisingMapper.selectLastAdvertising(id);
    }
}

package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.bean.BrandAddVo;
import com.cskaoyan.mall.bean.BrandExample;
import com.cskaoyan.mall.mapper.BrandMapper;
import com.cskaoyan.mall.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* *
@author  Walker-胡
@create 2020-06-28 15:49
*/
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandMapper brandMapper;
    @Override
    public Map<String, Object> queryBrandPageList(Integer page, Integer limit, Integer id, String name, String sort, String order) {
        Map<String, Object>  map = new HashMap<> ();
        PageHelper.startPage (page,limit);
        List<Brand> brands = brandMapper.queryBrandPageList (id, name, sort, order);
        PageInfo pageInfo = new PageInfo (brands);
        long total=pageInfo.getTotal ();
        map.put ("total",total);
        map.put("items",brands);
        return map;
    }

    @Override
    public Brand add(BrandAddVo brandAddVo) {
        Brand brand = new Brand ();
        BeanUtils.copyProperties (brandAddVo,brand);
        brand.setAddTime (new Date ());
        brand.setDeleted (false);
        brandMapper.insert (brand);
        return brand;
    }

    @Override
    public boolean delete(Brand brand) {
        brand.setDeleted (true);
        brand.setAddTime (new Date ());
        int i=brandMapper.updateByPrimaryKeySelective (brand);
        return i==1;
    }

    @Override
    public boolean updateByBrand(Brand brand) {
        brand.setUpdateTime (new Date ());
        int i=brandMapper.updateByPrimaryKeySelective (brand);
        System.out.println (i);
        return i==1;
    }
}

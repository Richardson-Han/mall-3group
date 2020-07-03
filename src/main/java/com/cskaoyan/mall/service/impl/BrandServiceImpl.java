package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.VO.BrandAddVo;
import com.cskaoyan.mall.bean.VO.wx.BrandGetListVO;
import com.cskaoyan.mall.bean.wx.WXBrandListData;
import com.cskaoyan.mall.constants.MallError;
import com.cskaoyan.mall.mapper.BrandMapper;
import com.cskaoyan.mall.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.tomcat.jni.Error;
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
    public BaseRespVo add(BrandAddVo brandAddVo) {
        Brand brand = new Brand ();
        BeanUtils.copyProperties (brandAddVo,brand);

        brand.setAddTime (new Date ());
        brand.setDeleted (false);
        int i= 0;
        try {
            i = brandMapper.insert (brand);
        } catch (Exception e) {
            e.printStackTrace ();
        }
        if(i==1){
            return BaseRespVo.ok (brand);
        }
        return BaseRespVo.error ("添加失败",888);
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

    @Override
    public WXBrandListData getBrandList(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BrandGetListVO> brands = brandMapper.selectBrandListVO();
        PageInfo<BrandGetListVO> pageInfo = new PageInfo<>(brands);
        long total = pageInfo.getTotal();
        return new WXBrandListData(brands,total);
    }

    @Override
    public Brand getBrandDetail(Integer id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        return brand;
    }


    public List<Brand> wxselectDirectSupply(){
        return brandMapper.selectDirectSupply();
    }
}

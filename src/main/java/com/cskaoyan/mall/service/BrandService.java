package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.bean.VO.BrandAddVo;
import com.cskaoyan.mall.bean.wx.WXBrandListData;

import java.util.Map;

/* *
@author  Walker-胡
@create 2020-06-28 15:48
*/

public interface BrandService {
    Map<String,Object> queryBrandPageList(Integer page,Integer limit,
                                          Integer id,String name,String sort,String order);
    Brand add(BrandAddVo brandAddVo);
    boolean delete(Brand brand);
    boolean updateByBrand(Brand brand);

    WXBrandListData getBrandList(Integer page, Integer size);

    Brand getBrandDetail(Integer id);
}

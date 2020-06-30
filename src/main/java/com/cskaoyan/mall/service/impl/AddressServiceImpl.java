package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Address;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.AddressExample;
import com.cskaoyan.mall.mapper.AddressMapper;
import com.cskaoyan.mall.mapper.RegionMapper;
import com.cskaoyan.mall.service.AddressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @author 社会主义好
 * @date 2020-06-26 19:52 星期五 
 *
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    RegionMapper regionMapper;

    @Override
    public BaseData queryAddress(Integer page, Integer limit, String sort, String order, String name, Integer userId) {

        PageHelper.startPage(page, limit);

        AddressExample addressExample = new AddressExample();
        addressExample.setOrderByClause(sort + " " + order);
        AddressExample.Criteria criteria = addressExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (name != null && !name.isEmpty()) {
            //criteria.andNameEqualTo(name);
            criteria.andNameLike("%" + name + "%");
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        List<Address> addresses = addressMapper.selectByExample(addressExample);
        for (Address address : addresses) {
            Integer areaId = address.getAreaId();
            Integer cityId = address.getCityId();
            Integer provinceId = address.getProvinceId();
            address.setArea(regionMapper.selectByPrimaryKey(areaId).getName());
            address.setCity(regionMapper.selectByPrimaryKey(cityId).getName());
            address.setProvince(regionMapper.selectByPrimaryKey(provinceId).getName());
        }
        PageInfo<Address> pageInfo = new PageInfo<>(addresses);
        long total = pageInfo.getTotal();
        return new BaseData(addresses, total);
    }
}

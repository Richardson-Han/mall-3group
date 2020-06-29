package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Region;
import com.cskaoyan.mall.bean.RegionExample;
import com.cskaoyan.mall.mapper.RegionMapper;
import com.cskaoyan.mall.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

/* *
@author  Walker-胡
@create 2020-06-28 12:42
*
*查询所有省份地区详细地址
*/
@Service
@Transactional
public class RegionServiceImpl implements RegionService {
    @Autowired
    RegionMapper regionMapper;
    @Override
    public List<Region> queryRegionList() {

        //查询一级(省份)
        RegionExample oneRegionExample = new RegionExample ();
        oneRegionExample.createCriteria ().andTypeEqualTo ((byte)1);
        List<Region > oneRegions=regionMapper.selectByExample (oneRegionExample);

        //查询二级 （市区，县）
        RegionExample twoRegionExample = new RegionExample ();
        twoRegionExample.createCriteria ().andTypeEqualTo ((byte)2);
        List<Region> twoRegions=regionMapper.selectByExample (twoRegionExample);

        //查询三级（区）
        RegionExample threeRegionExample = new RegionExample();
        threeRegionExample.createCriteria().andTypeEqualTo((byte) 3);
        List<Region> threeRegions = regionMapper.selectByExample(threeRegionExample);

        /*
        * 先根据type查出省级市级和区级，如上所示
        * 然后遍历，每个集合，判断市级的pid是否等于省级的id
        * 如果等于把该市级添加到改省级的chidren中
        * */
        for (int i = 0; i <twoRegions.size () ; i++) {
            for (int j = 0; j <threeRegions.size () ; j++) {
                Region two=twoRegions.get (i);
                Region three=threeRegions.get (j);
                if (two.getId ()==three.getPid ()){
                    two.getChildren ().add (three);
                }
            }
        }

        for (int i = 0; i <oneRegions.size () ; i++) {
            for (int j = 0; j <twoRegions.size () ; j++) {
                Region one=oneRegions.get (i);
                Region two = twoRegions.get (j);
                if(one.getId ()==two.getPid ()){
                    one.getChildren ().add (two);
                }
            }

        }
        return oneRegions;
    }
}

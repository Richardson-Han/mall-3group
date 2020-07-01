package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Region;
import org.springframework.stereotype.Service;

import java.util.List;

/* *
@author  Walker-胡
@create 2020-06-28 11:41
*/

public interface RegionService {
    List<Region> queryRegionList();

    List<Region> queryWXRegionList(Integer pid);
}

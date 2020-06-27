package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Advertising;
import com.cskaoyan.mall.bean.BaseData;

import java.util.Date;


/**
 * @create 2020-06-26 19:58
 */
public interface AdvertisingService {
    BaseData queryAdvertising(Integer page,Integer limit,String sort,String order);

    Integer insertAdvertising(Advertising advertising);
}

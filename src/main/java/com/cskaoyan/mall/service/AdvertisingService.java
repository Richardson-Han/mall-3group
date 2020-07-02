package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Advertising;
import com.cskaoyan.mall.bean.BaseData;

import java.util.List;


/**
 * @author 韩
 * @create 2020-06-26 19:58
 */
public interface AdvertisingService {
    BaseData queryAdvertising(Integer page,Integer limit,String sort,String order);

    Integer insertAdvertising(Advertising advertising);

    Integer updateAdvertising(Advertising advertising);

    Integer deleteAdvertising(Advertising advertising);

    List<Advertising> wxselectTopAdvertising();

    Advertising selectLastAdvertising();
}

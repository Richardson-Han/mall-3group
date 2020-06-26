package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.Advertising;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.BaseRespVo;
import com.cskaoyan.mall.service.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @create 2020-06-26 19:16
 */
@RestController
@RequestMapping("admin/ad")
public class AdvertisingController {

    @Autowired
    AdvertisingService advertisingService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit, String sort, String order) {
        BaseData baseData = advertisingService.queryAdvertising(page, limit, sort, order);
        return BaseRespVo.ok(baseData);
    }

    @RequestMapping("create")
    public BaseRespVo create(Integer id, String name, String url, Byte position,
                             String content, Boolean enabled, Date addTime, Date updateTime) {
        Advertising advertising = new Advertising();
        advertising.setId(id);
        advertising.setName(name);
        advertising.setUrl(url);
        advertising.setPosition(position);
        advertising.setContent(content);
        advertising.setEnabled(enabled);
        advertising.setAddTime(addTime);
        advertising.setUpdateTime(updateTime);
        Integer insert = advertisingService.insertAdvertising(advertising);
        if (insert == 1) {
            return BaseRespVo.ok(advertisingService.
                    queryAdvertising(1, 20, "add_time", "desc"));
        } else
            return BaseRespVo.error();
    }
}

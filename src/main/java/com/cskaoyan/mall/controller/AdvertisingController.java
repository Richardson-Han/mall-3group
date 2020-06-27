package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.Advertising;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.BaseRespVo;
import com.cskaoyan.mall.service.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
    public BaseRespVo create(@RequestBody Advertising advertising) {
        Integer insert = advertisingService.insertAdvertising(advertising);
        if (insert == 1) {
            return BaseRespVo.ok(advertisingService.
                    queryAdvertising(0, 20, "add_time", "desc"));
        } else {
            return BaseRespVo.error();
        }
    }

    @RequestMapping("update")
    public BaseRespVo update(@RequestBody Advertising advertising) {
        advertising.setUpdateTime(new Date());
        Integer updateAdvertising = advertisingService.updateAdvertising(advertising);
        if (updateAdvertising == 1) {
            return BaseRespVo.ok(advertisingService.
                    queryAdvertising(1, 20, "add_time", "desc"));
        } else {
            return BaseRespVo.error();
        }

    }

    /**
     * 做虚拟删除，仅更改deleted列数据为ture
     */
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Advertising advertising) {
        Integer deleteAdvertising = advertisingService.deleteAdvertising(advertising);
        System.out.println("id = " + advertising.getId() + ",deleted = " + advertising.getDeleted());
        if (deleteAdvertising == 1) {
            return BaseRespVo.ok();
        } else {
            return BaseRespVo.error("删除失败", 404);
        }
    }
}

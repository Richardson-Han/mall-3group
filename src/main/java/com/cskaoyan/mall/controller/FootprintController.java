package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.BaseRespVo;
import com.cskaoyan.mall.service.FootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author 社会主义好
 * @date 2020-06-27 7:40 星期六 
 *
 */
@RestController
@RequestMapping("admin/footprint")
public class FootprintController {
    @Autowired
    FootprintService footprintService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page,Integer limit,String sort,String order, Integer userId, Integer goodsId){
        BaseData baseData = footprintService.queryFootprint(page, limit, sort, order, userId, goodsId);
        return BaseRespVo.ok(baseData);
    }
}

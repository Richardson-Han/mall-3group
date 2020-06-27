package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.BaseRespVo;
import com.cskaoyan.mall.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author 社会主义好
 * @date 2020-06-27 7:01 星期六 
 *
 */
@RequestMapping("admin/collect")
@RestController
public class CollectController {

    @Autowired
    CollectService collectService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit, String sort, String order, Integer userId, Integer valueId){
        BaseData baseData = collectService.queryCollect(page, limit, sort, order, userId, valueId);
        return BaseRespVo.ok(baseData);
    }
}

package com.cskaoyan.mall.controller;


import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.BaseRespVo;
import com.cskaoyan.mall.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/log")
public class LogController {

    @Autowired
    LogService logService;

    @RequestMapping("list")
    public BaseRespVo getLogList(Integer page,Integer limit,String name,String sort,String order){
        BaseData baseData = logService.getLogList(page,limit,name,sort,order);
        return BaseRespVo.ok(baseData);
    }

}

package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BO.ConfigExpressBO;
import com.cskaoyan.mall.bean.BO.ConfigMallBO;
import com.cskaoyan.mall.bean.BO.ConfigOrderBO;
import com.cskaoyan.mall.bean.BO.ConfigWXBO;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/config")
public class ConfigController {
    @Autowired
    ConfigService configService;
    /**
     * 查看商场配置
     * @return
     */
    @GetMapping("mall")
    public BaseRespVo mall(){
        ConfigMallBO configMallBO = configService.queryConfigMall();
        return BaseRespVo.ok(configMallBO);
    }

    /**
     * 更改商场配置
     * @param configMallBO
     * @return
     */
    @PostMapping("mall")
    public BaseRespVo mall(@RequestBody ConfigMallBO configMallBO){
        configService.updateConfigMall(configMallBO);
        return BaseRespVo.ok();
    }

    /**
     * 查看运费配置
     * @return
     */
    @GetMapping("express")
    public BaseRespVo express(){
        ConfigExpressBO express =  configService.queryConfigExpress();
        return BaseRespVo.ok(express);
    }

    /**
     *更改运费设置
     */
    @PostMapping("express")
    public BaseRespVo express(@RequestBody ConfigExpressBO configExpressBO){
        configService.updateConfigExpress(configExpressBO);
        return BaseRespVo.ok();
    }

    /**
     * 查看订单配置
     * @return
     */
    @GetMapping("order")
    public BaseRespVo order(){
        ConfigOrderBO configOrderBO = configService.queryConfigOrder();
        return BaseRespVo.ok(configOrderBO);
    }

    /**
     * 更改订单相关配置
     * @param orderBO
     * @return
     */
    @PostMapping("order")
    public BaseRespVo order(@RequestBody ConfigOrderBO orderBO){
        configService.updateConfigOrder(orderBO);
        return BaseRespVo.ok();
    }

    /**
     * 查看小程序配置
     * @return
     */
    @GetMapping("wx")
    public BaseRespVo wx(){
        ConfigWXBO configWXBO = configService.queryConfigWX();
        return BaseRespVo.ok(configWXBO);
    }

    @PostMapping("wx")
    public BaseRespVo wx(@RequestBody ConfigWXBO configWXBO){
        configService.updateConfigWX(configWXBO);
        return BaseRespVo.ok();
    }
}

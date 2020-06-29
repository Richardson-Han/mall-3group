package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.VO.StatBaseVO;
import com.cskaoyan.mall.service.GoodsService;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/stat")
public class StatController {
    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    /**
     * 统计用户数
     * @return
     */
    @RequestMapping("user")
    public BaseRespVo user (){
        StatBaseVO statUserVO = userService.getUserStat();
        return  BaseRespVo.ok(statUserVO);
    }

    /**
     * 统计订单数
     * @return
     */
    @RequestMapping("order")
    public BaseRespVo order(){
        StatBaseVO statOrderVO = orderService.getOrderStat();
        return BaseRespVo.ok(statOrderVO);
    }

    /**
     * 统计商品
     * @return
     */
    @RequestMapping("goods")
    public BaseRespVo goods(){
        StatBaseVO statBaseVO = goodsService.getGoodsStat();
        return BaseRespVo.ok(statBaseVO);
    }
}

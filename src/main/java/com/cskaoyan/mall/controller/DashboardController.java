package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.GoodsProductService;
import com.cskaoyan.mall.service.GoodsService;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/***
 * @author 社会主义好
 * @date 2020-06-26 17:06 星期五 
 *
 */
@RestController
public class DashboardController {

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    GoodsProductService goodsProductService;

    @RequestMapping("admin/dashboard")
    public BaseRespVo dashboard(){
        Long goodsTotal = goodsService.getGoodsTotal();
        Long orderTotal = orderService.getOrderTotal();
        Long productTotal = goodsProductService.getProductTotal();
        Long userTotal = userService.getUserTotal();
        Map totalMap = new HashMap();
        totalMap.put("goodsTotal",goodsTotal);
        totalMap.put("orderTotal",orderTotal);
        totalMap.put("productTotal",productTotal);
        totalMap.put("userTotal",userTotal);
        return BaseRespVo.ok(totalMap);
    }
}

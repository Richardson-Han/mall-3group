package com.cskaoyan.mall.controller;


import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/* *
@author  Walker-胡
@create 2020-06-29 8:57
*/
@RestController
@RequestMapping("/admin/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @GetMapping("/list" )
    public BaseRespVo orderPageList(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit,
            @RequestParam("sort")  String sort,
            @RequestParam("order") String order,
            @RequestParam(value = "orderStatusArray",required = false) List<Integer> orderStatusArray,
            @RequestParam(value = "userId",required = false) Integer userId,
            @RequestParam(value = "orderSn",required = false) Integer orderSn
    ){
        Map<String, Object> map=orderService.queryOrderPageList(page,limit,sort,order,orderStatusArray,userId,orderSn);
        return  BaseRespVo.ok (map);
    }
}

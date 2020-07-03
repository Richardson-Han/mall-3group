package com.cskaoyan.mall.controller;


import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.VO.OrderRefundVO;
import com.cskaoyan.mall.bean.VO.ShipVO;
import com.cskaoyan.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    @GetMapping ("/list" )
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
    /*
    * 显示商品详情
    * */
    @GetMapping("/detail")
    public  BaseRespVo getDetail(@RequestParam("id") Integer id){
        Map<String, Object> map = orderService.queryOrderDetailById (id);
        return BaseRespVo.ok (map);
    }

    @PostMapping ("/refund")
    public  BaseRespVo refund(
//    @RequestParam("orderId") Integer orderId,
//                              @RequestParam("refundMoney" ) BigDecimal refundMoney
    @RequestBody  OrderRefundVO orderRefundVO
                              ){
        orderService.orderRefund(orderRefundVO);
        return BaseRespVo.ok ();
    }
    /*
    * 订单已发货
    * */
    @PostMapping ("/ship")
    public  BaseRespVo refund(@RequestBody ShipVO shipVO){
        orderService.orderShip(shipVO);
        return BaseRespVo.ok ();
    }
}

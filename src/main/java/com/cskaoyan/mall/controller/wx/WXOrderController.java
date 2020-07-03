package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.GoodsComment;
import com.cskaoyan.mall.bean.Order;
import com.cskaoyan.mall.bean.OrderGoods;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.wx.BO.OrderCommentBO;
import com.cskaoyan.mall.bean.wx.HandleOption;
import com.cskaoyan.mall.bean.wx.VO.*;
import com.cskaoyan.mall.bean.wx.WXOrderGoods;
import com.cskaoyan.mall.service.GoodsCommentService;
import com.cskaoyan.mall.service.GroupService;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.utils.WXTokenUtils;
import com.cskaoyan.mall.service.UserService;
import com.cskaoyan.mall.utils.WxHandleOptionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/order")
public class WXOrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsCommentService commentService;

    @Autowired
    GroupService groupService;

    @Autowired
    UserService userService;



    @RequestMapping("comment")
    @Transactional
    @RequiresAuthentication
    public BaseRespVo comment(@RequestBody OrderCommentBO commentBO){
        //先在comment表添加评论
        GoodsComment goodsComment = new GoodsComment();
        goodsComment.setContent(commentBO.getContent());
        goodsComment.setDeleted(false);
        goodsComment.setAddTime(new Date());
        goodsComment.setHasPicture(commentBO.getHasPicture());
        goodsComment.setPicUrls(commentBO.getPicUrls());
        goodsComment.setStar(commentBO.getStar());
        goodsComment.setType((byte) 3);
        goodsComment.setUpdateTime(new Date());
        //获得userId
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipals().getPrimaryPrincipal();
        Integer userId = userService.wxselectIdByUsername(username);
        goodsComment.setUserId(userId);
        //根据orderId从order_goods表中获取商品id
        Integer goodsId = orderService.queryGoodsIdByOrderId(commentBO.getOrderGoodsId());
        goodsComment.setValueId(goodsId);
        //插入数据
        commentService.insertComment(goodsComment);

        //再更新order_goods表的comment的id
        //获得comment的id
        Integer commentId = commentService.selectTheLastInsertId();
        //更新
        orderService.updateCommentId(commentBO.getOrderGoodsId(), commentId);
        return BaseRespVo.ok();
    }


    @RequestMapping("list")
    @RequiresAuthentication
    public BaseRespVo list(Integer showType, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Order> orders = orderService.queryOrderByOrderStatus(showType);
        List<WXOrderGoods> goodsList;
        List<OrderListDataVO> orderListDataVOs = new ArrayList<>();
        for (Order order : orders) {
            OrderListDataVO orderListDataVO = new OrderListDataVO();
            orderListDataVO.setActualPrice(order.getActualPrice());
            goodsList = orderService.queryOrderGoodsByOrderId(order.getId());
            orderListDataVO.setGoodsList(goodsList);
            HandleOption handleOption = WxHandleOptionUtil.getHandleOption(order.getOrderStatus());
            orderListDataVO.setHandleOption(handleOption);
            orderListDataVO.setId(order.getId());
            orderListDataVO.setIsGroupin(groupService.isGroupIn(order.getId()));
            orderListDataVO.setOrderSn(order.getOrderSn());
            orderListDataVO.setOrderStatusText(order.getOrderStatus());

            orderListDataVOs.add(orderListDataVO);
        }
        // List<Order> orders = orderService.queryOrderByOrderStatus(showType); 这个 list 通过分页插件处理后的结果，
        // 表面上是 List 类型，实际是 Page( extends ArrayList)，下面这条代码传入的orderListDataVOs包含的page信息已经不同
        //PageInfo<OrderListDataVO> pageInfo = new PageInfo<>(orderListDataVOs);
        PageInfo<Order> orderPageInfo = new PageInfo<>(orders);
        int totalPages = orderPageInfo.getPages();
        OrderListBaseVO orderListBaseVO = new OrderListBaseVO(orders.size(), orderListDataVOs, totalPages);
        return BaseRespVo.ok(orderListBaseVO);
    }

    @RequestMapping("detail")
    @RequiresAuthentication
    public BaseRespVo detail(Integer orderId) {
        List<OrderGoods> orderGoodsList = orderService.selectOrderGoodsByOrderId(orderId);
        WXOrderInfoVO orderInfo = orderService.getWxOrderInfo(orderId);
        WXOrderDetailDataVO wxOrderDetailDataVO = new WXOrderDetailDataVO(orderGoodsList, orderInfo);
        return BaseRespVo.ok(wxOrderDetailDataVO);
    }

    @PostMapping("cancel")
    @RequiresAuthentication
    public BaseRespVo cancel(@RequestBody Map map) {
        orderService.cancelOrder((Integer) map.get("orderId"));
        return BaseRespVo.ok();
    }

    @RequestMapping("refund")
    @RequiresAuthentication
    public BaseRespVo refund(@RequestBody Map map) {
        orderService.refund((Integer) map.get("orderId"));
        return BaseRespVo.ok();
    }

    @RequestMapping("delete")
    @RequiresAuthentication
    public BaseRespVo delete(@RequestBody Map map) {
        orderService.deleteOrder((Integer) map.get("orderId"));
        return BaseRespVo.ok();
    }

    @RequestMapping("confirm")
    @RequiresAuthentication
    public BaseRespVo confirm(@RequestBody Map map) {
        orderService.confirmOrder((Integer) map.get("orderId"));
        return BaseRespVo.ok();
    }

    @RequestMapping("goods")
    @RequiresAuthentication
    public BaseRespVo goods(Integer orderId, Integer goodsId) {
        OrderGoods orderGoods = orderService.getOrderGoods(orderId, goodsId);
        return BaseRespVo.ok(orderGoods);
    }


    //方惠
    //订单提交
    //付款操作不用写
    @RequestMapping("submit")
    public BaseRespVo submit(@RequestBody Map map, HttpServletRequest request) {
        String username = WXTokenUtils.requestToUsername(request);
        OrderSubmitVO orderSubmitVO = orderService.submit(map, username);
        return BaseRespVo.ok(orderSubmitVO);
    }
}

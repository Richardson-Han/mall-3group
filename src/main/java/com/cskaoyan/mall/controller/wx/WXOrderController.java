package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.GoodsComment;
import com.cskaoyan.mall.bean.GroupOnRules;
import com.cskaoyan.mall.bean.Order;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.wx.BO.OrderCommentBO;
import com.cskaoyan.mall.bean.wx.HandleOption;
import com.cskaoyan.mall.bean.wx.OrderGoods;
import com.cskaoyan.mall.bean.wx.VO.OrderListBaseVO;
import com.cskaoyan.mall.bean.wx.VO.OrderListDataVO;
import com.cskaoyan.mall.service.GoodsCommentService;
import com.cskaoyan.mall.service.GroupService;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.utils.WxHandleOptionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



    @RequestMapping("comment")
    @Transactional
    public BaseRespVo comment(OrderCommentBO commentBO){
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
        //没有整合shiro ，先暂时手动填入
        goodsComment.setUserId(1);
        //根据orderId从order_goods表中获取商品id
        Integer goodsId = orderService.queryGoodsIdByOrderId(commentBO.getOrderGoodsId());
        goodsComment.setValueId(goodsId);
        //插入数据
        commentService.insertComment(goodsComment);

        //再更新order_goods表的comment的id
        //获得comment的id
        Integer commentId = commentService.selectTheLastInsertId();
        //更新
        orderService.updateCommentId(commentBO.getOrderGoodsId(),commentId);
        return BaseRespVo.ok();
    }


    @RequestMapping("list")
    public BaseRespVo list(Integer showType, Integer page, Integer size){
        PageHelper.startPage(page,size);
        List<Order> orders = orderService.queryOrderByOrderStatus(showType);
        List<OrderGoods> goodsList;
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
        PageInfo<OrderListDataVO> pageInfo = new PageInfo<>(orderListDataVOs);
        int totalPages = pageInfo.getPages();
        OrderListBaseVO orderListBaseVO = new OrderListBaseVO(orders.size(), orderListDataVOs, totalPages);
        return BaseRespVo.ok(orderListBaseVO);
    }

    @RequestMapping("detail")
    public BaseRespVo detail(Integer orderId){
        return null;
    }

}

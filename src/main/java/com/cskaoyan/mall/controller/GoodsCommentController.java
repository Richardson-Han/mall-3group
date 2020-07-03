package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.*;

import com.cskaoyan.mall.bean.BO.GoodsCommentBO;
import com.cskaoyan.mall.bean.BO.GoodsCommentListBO;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.GoodsCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/")
public class GoodsCommentController {

    @Autowired
    GoodsCommentService commentService;

    /**
     *  商品评论
     *  查询商品评论
     */
    @RequestMapping("comment/list")
    public BaseRespVo list(GoodsCommentListBO commentListBO){
        BaseData baseData = commentService.queryComment(commentListBO);
        return BaseRespVo.ok(baseData);
    }

    /**
     *  假删除
     */
    @RequestMapping("comment/delete")
    public BaseRespVo delete(@RequestBody GoodsComment goodsComment){
        commentService.deleteComment(goodsComment);
        return BaseRespVo.ok();
    }

    /**
     *  回复评论，但是接口本身无法评论，因为对应数据库里有内容
     */
    @RequestMapping("order/reply")
    public BaseRespVo reply(@RequestBody GoodsCommentBO commentBO){
        GoodsComment goodsComment = commentService.replyComment(commentBO);
        if(goodsComment == null){
            return BaseRespVo.errorString("订单商品已回复");
        }
        return BaseRespVo.ok(goodsComment);
    }



}

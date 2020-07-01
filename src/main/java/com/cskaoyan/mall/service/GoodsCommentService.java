package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BO.wx.WXGoodCommentBo;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.GoodsComment;
import com.cskaoyan.mall.bean.BO.GoodsCommentBO;
import com.cskaoyan.mall.bean.BO.GoodsCommentListBO;

import java.util.List;

public interface GoodsCommentService {

    BaseData queryComment(GoodsCommentListBO commentListBO);

    void deleteComment(GoodsComment goodsComment);

    int replyComment(GoodsCommentBO commentBO);

    List<Object> getWXCommentList(WXGoodCommentBo wxGoodCommentBo);

}

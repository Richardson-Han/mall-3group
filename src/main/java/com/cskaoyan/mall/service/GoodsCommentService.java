package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.GoodsComment;
import com.cskaoyan.mall.bean.BO.GoodsCommentBO;
import com.cskaoyan.mall.bean.BO.GoodsCommentListBO;

public interface GoodsCommentService {

    BaseData queryComment(GoodsCommentListBO commentListBO);

    void deleteComment(GoodsComment goodsComment);

    int replyComment(GoodsCommentBO commentBO);
}

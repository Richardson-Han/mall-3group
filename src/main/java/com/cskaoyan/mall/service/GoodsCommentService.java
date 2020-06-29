package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.GoodsComment;
import com.cskaoyan.mall.bean.GoodsCommentBO;
import com.cskaoyan.mall.bean.GoodsCommentListBO;

public interface GoodsCommentService {

    BaseData queryComment(GoodsCommentListBO commentListBO);

    void deleteComment(GoodsComment goodsComment);

    int replyComment(GoodsCommentBO commentBO);
}

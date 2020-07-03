package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BO.wx.PostCommentBO;
import com.cskaoyan.mall.bean.BO.wx.WXGoodCommentBo;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Comment;
import com.cskaoyan.mall.bean.GoodsComment;
import com.cskaoyan.mall.bean.BO.GoodsCommentBO;
import com.cskaoyan.mall.bean.BO.GoodsCommentListBO;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


public interface GoodsCommentService {

    BaseData queryComment(GoodsCommentListBO commentListBO);

    void deleteComment(GoodsComment goodsComment);

    GoodsComment replyComment(GoodsCommentBO commentBO);

    //显示所有评论
    Map<String, Object> getWXCommentList(WXGoodCommentBo wxGoodCommentBo);


    void insertComment(GoodsComment goodsComment);

    Integer selectTheLastInsertId();

    Map<String, Object> getWXCount(WXGoodCommentBo wxGoodCommentBo);

    GoodsComment getWXPost(PostCommentBO postCommentBO);
}

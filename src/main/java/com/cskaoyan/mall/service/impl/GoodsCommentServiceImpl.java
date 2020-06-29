package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.BO.GoodsCommentBO;
import com.cskaoyan.mall.bean.BO.GoodsCommentListBO;
import com.cskaoyan.mall.mapper.GoodsCommentMapper;
import com.cskaoyan.mall.service.GoodsCommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsCommentServiceImpl implements GoodsCommentService {

    @Autowired
    GoodsCommentMapper commentMapper;

    /**
     *  查询商品评论
     * @param commentListBO
     * @return
     */
    @Override
    public BaseData queryComment(GoodsCommentListBO commentListBO) {
        Integer page = commentListBO.getPage();
        Integer limit = commentListBO.getLimit();
        String sort = commentListBO.getSort();
        String order = commentListBO.getOrder();

        GoodsCommentExample commentExample = new GoodsCommentExample();
        commentExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        List<GoodsComment> comments = new ArrayList<>();
        long total = 0;
        String userId = commentListBO.getUserId();
        Integer valueId = commentListBO.getValueId();
        //userId为空， valueId不为空
        if(userId == null && valueId != null){
            commentExample.createCriteria().andValueIdEqualTo(valueId);
        }else if(userId != null && valueId == null){
            //userId不为空， valueId为空
            //需要转换下userId的类型
            commentExample.createCriteria().andUserIdEqualTo(Integer.parseInt(userId));
        }else if(userId != null && valueId != null){
            //userId不为空， valueId不为空
            commentExample.createCriteria().andValueIdEqualTo(valueId).andUserIdEqualTo(Integer.parseInt(userId));
        }
        commentExample.createCriteria().andDeletedEqualTo(false);
        comments = commentMapper.selectByExample(commentExample);
        PageInfo<GoodsComment> commentPageInfo = new PageInfo<>(comments);
        total = commentPageInfo.getTotal();
        return new BaseData(comments, total);
    }

    /**
     *  删除评论
     */
    @Override
    public void deleteComment(GoodsComment goodsComment) {
        goodsComment.setDeleted(true);
        commentMapper.updateByPrimaryKeySelective(goodsComment);
    }

    /**
     *  回复评论
     */
    @Override
    public int replyComment(GoodsCommentBO commentBO) {
        Integer id = commentBO.getCommentId();
        GoodsComment comment = commentMapper.selectByPrimaryKey(id);
        //id对应的content有内容，则无法回复
        if(comment.getContent() == null){
            comment.setContent(commentBO.getContent());
            commentMapper.updateByPrimaryKeySelective(comment);
            return 1;
        }
        return 0;
    }
}

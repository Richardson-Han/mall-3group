package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.BO.wx.PostCommentBO;
import com.cskaoyan.mall.bean.GoodsComment;
import com.cskaoyan.mall.bean.BO.GoodsCommentBO;
import com.cskaoyan.mall.bean.BO.GoodsCommentListBO;
import com.cskaoyan.mall.bean.BO.wx.WXGoodCommentBo;
import com.cskaoyan.mall.bean.VO.wx.WXCommentVO;
import com.cskaoyan.mall.bean.VO.wx.WXTopicVO;
import com.cskaoyan.mall.bean.VO.wx.WXUserInfoVO;
import com.cskaoyan.mall.mapper.GoodsCommentMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.GoodsCommentService;
import com.cskaoyan.mall.utils.WXTokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class GoodsCommentServiceImpl implements GoodsCommentService {

    @Autowired
    GoodsCommentMapper commentMapper;
    @Autowired
    UserMapper UserMapper;


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
    //胡小强
    @Override

    public Map<String,Object>  getWXCommentList(WXGoodCommentBo wxGoodCommentBo){
        Map<String,Object> map =new HashMap<> ();
        List<Object> lsit = new ArrayList<> ();
        //查询测试用户名

        String username = (String) SecurityUtils.getSubject().getPrincipal();;
        UserExample userExample = new UserExample ();
        UserExample.Criteria criteria = userExample.createCriteria ().andUsernameEqualTo (username);
        WXUserInfoVO wxUserInfoVO = UserMapper.selectUserInfoByUsername (username);
        //分页
        PageHelper.startPage (wxGoodCommentBo.getPage (),wxGoodCommentBo.getSize ());
        List<WXCommentVO> commentList =new ArrayList<> ();

        GoodsCommentExample comExample = new GoodsCommentExample ();
        comExample.setOrderByClause ("add_time desc");
        comExample.createCriteria ().andValueIdEqualTo (wxGoodCommentBo.getValuedId ());

        List<GoodsComment> goodsCommentListComments = commentMapper.selectByExample (comExample);
        for (GoodsComment comment : goodsCommentListComments) {
            WXCommentVO wxVO = new WXCommentVO ();
            wxVO.setAddTime (comment.getAddTime ());
            wxVO.setContent (comment.getContent ());
            wxVO.setPicList (comment.getPicUrls ());
            wxVO.setUserInfo (wxUserInfoVO);
            commentList.add (wxVO);
        }


        PageInfo <GoodsComment> pageInfo=new PageInfo<> (goodsCommentListComments);
        long count =pageInfo.getTotal ();

        map.put ("count",count);
        map.put("currentPage",wxGoodCommentBo.getPage ());
        map.put ("data",commentList);
        return map;
    }



    @Override
    public void insertComment(GoodsComment  goodsComment) {
        commentMapper.insertSelective(goodsComment);
    }

    @Override
    public Integer selectTheLastInsertId() {
        Integer id = commentMapper.selectTheLastInsertId();
        return id;

    }

    @Override
    public Map<String, Object> getWXCount(WXGoodCommentBo wxGoodCommentBo) {
        Map<String,Object> countmap=new HashMap<> ();
        //查询所有的评论
        GoodsCommentExample goodsCommentExample = new GoodsCommentExample ();
        GoodsCommentExample.Criteria criteria = goodsCommentExample.createCriteria ()
                .andTypeEqualTo (wxGoodCommentBo.getType ())
                .andValueIdEqualTo (wxGoodCommentBo.getValuedId ());
        long allCount= commentMapper.countByExample (goodsCommentExample);
        countmap.put ("allCount",allCount);
        //查询所有有图片的评论
        GoodsCommentExample goodsExample2 = new GoodsCommentExample ();
        GoodsCommentExample.Criteria criteria1 = goodsExample2.createCriteria ()
                .andHasPictureEqualTo (true)
                .andTypeEqualTo (wxGoodCommentBo.getType ())
                .andValueIdEqualTo (wxGoodCommentBo.getValuedId ());
        long hasPicCount = commentMapper.countByExample (goodsExample2);
        countmap.put ("hasPicCount",hasPicCount);
        return countmap;
    }

    @Override
    public GoodsComment getWXPost(PostCommentBO postCommentBO) {
        GoodsComment goodsComment = new GoodsComment ();
        goodsComment.setAddTime (new Date ());
        goodsComment.setContent (postCommentBO.getContent ());
        goodsComment.setHasPicture (postCommentBO.getHasPicture ());
        //id不知道怎么设置，就没加
        goodsComment.setPicUrls (postCommentBO.getPicUrls ());
        goodsComment.setStar (postCommentBO.getStar ());
        goodsComment.setType (postCommentBO.getType ());
        goodsComment.setUpdateTime (new Date ());
        //userId不知道怎么设置，写成1
        goodsComment.setUserId (1);
        goodsComment.setValueId (postCommentBO.getValuedId ());


        return goodsComment;
    }

}

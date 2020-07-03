package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.BO.wx.PostCommentBO;
import com.cskaoyan.mall.bean.BO.wx.WXGoodCommentBo;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Comment;
import com.cskaoyan.mall.bean.GoodsComment;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.GoodsCommentService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

/* *
@author  Walker-胡
@create 2020-07-01 20:56
*/
@RestController
@RequestMapping("/wx/comment")

public class WXCommentController {
    @Autowired
    GoodsCommentService goodsCommentService;


    /**
     * @RequestParam("valueId")Integer valueId,
     * @RequestParam("type") Byte type,
     * @RequestParam("size") Integer size,
     * @RequestParam("page") Integer page,
     * @RequestParam("showType") Integer showType
     * <p>
     * 显示所有评论
     */
    @RequiresAuthentication

    @RequestMapping("/list")
    public BaseRespVo getCommentList(Integer valueId, Byte type, Byte showType, Integer page, Integer size) {
        WXGoodCommentBo wxGoodCommentBo = new WXGoodCommentBo(type, size, page, showType, valueId);
        Map<String, Object> map = goodsCommentService.getWXCommentList(wxGoodCommentBo);
        return BaseRespVo.ok(map);
    }

    @RequestMapping("/count")
    public BaseRespVo getWXCount(Integer valueId, Byte type, Byte showType, Integer page, Integer size) {
        WXGoodCommentBo wxGoodCommentBo = new WXGoodCommentBo(type, size, page, showType, valueId);
        Map<String, Object> map = goodsCommentService.getWXCount(wxGoodCommentBo);
        return BaseRespVo.ok(map);
    }

    //老师任务尚没写，但有这个接口，在专题下有个评论
    @PostMapping("/post")
    public BaseRespVo getWXPost(@RequestBody PostCommentBO postCommentBO) {
        GoodsComment comment = goodsCommentService.getWXPost(postCommentBO);
        return BaseRespVo.ok(comment);
    }
}

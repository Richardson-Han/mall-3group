package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.BO.wx.WXGoodCommentBo;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.GoodsCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @GetMapping("/list")
    /*
        @RequestParam("valueId")Integer valueId,
                                     @RequestParam("type")     Byte type,
                                     @RequestParam("size")      Integer size,
                                     @RequestParam("page")      Integer page,
                                     @RequestParam("showType")  Integer showType

                                     显示所有评论

    * */
    public BaseRespVo getCommentList(@RequestBody WXGoodCommentBo wxGoodCommentBo, HttpServletRequest request){
        Map<String,Object> map=goodsCommentService.getWXCommentList(wxGoodCommentBo, request);
        return BaseRespVo.ok (map);
    }

    @GetMapping("/count")
    public BaseRespVo getWXCount(@RequestBody WXGoodCommentBo wxGoodCommentBo){
        Map<String,Object> map= goodsCommentService.getWXCount(wxGoodCommentBo);
           return BaseRespVo.ok (map);
    }
}

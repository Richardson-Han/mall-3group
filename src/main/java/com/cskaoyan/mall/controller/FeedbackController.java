package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.FeedbackService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author 社会主义好
 * @date 2020-06-27 9:08 星期六 
 *
 */
@RestController
@RequestMapping("admin/feedback")
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @RequestMapping("list")
    @RequiresAuthentication
    public BaseRespVo list(Integer page, Integer limit, String sort, String order, String username, Integer id){
        BaseData baseData = feedbackService.queryFeedback(page, limit, sort, order, username, id);
        return BaseRespVo.ok(baseData);
    }

}

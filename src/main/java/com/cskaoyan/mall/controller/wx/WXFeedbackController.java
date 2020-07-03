package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.Feedback;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author ponfy
 * @create 2020-07-02-15:38
 */
@RestController
@RequestMapping("wx/feedback")
public class WXFeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @RequestMapping("submit")
    public BaseRespVo submit(@RequestBody Feedback feedback){
        feedbackService.submitFeedBack(feedback);
        return BaseRespVo.ok();
    }
}

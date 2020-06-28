package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.Topic;
import com.cskaoyan.mall.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @create 2020-06-27 1:08
 */
@RestController
@RequestMapping("admin/topic")
public class TopicController {
    @Autowired
    TopicService topicService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit, String sort, String order) {
        BaseData baseData = topicService.queryTopic(page, limit, sort, order);
        return BaseRespVo.ok(baseData);
    }

    /**
     * 创建
     * 因网页未提供updatetime、addtime 需自行注入（service）
     */
    @RequestMapping("create")
    public BaseRespVo create(@RequestBody Topic topic) {
        Integer insert = topicService.createTopic(topic);
        if (insert == 1) {
            topic.setGoods("[]");
            return BaseRespVo.ok(topic);
        } else {
            return BaseRespVo.error("创建失败", 404);
        }
    }

    @RequestMapping("update")
    public BaseRespVo update(@RequestBody Topic topic){
        Integer insert = topicService.updateTopic(topic);
        if (insert == 1){
            return BaseRespVo.ok(topic);
        }else {
            return BaseRespVo.error("修改失败",999);
        }
    }

    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Topic topic) {
        Integer insert = topicService.deleteTopic(topic);
        if (insert == 1) {
            return BaseRespVo.ok();
        } else {
            return BaseRespVo.error("删除失败", 404);
        }

    }
}

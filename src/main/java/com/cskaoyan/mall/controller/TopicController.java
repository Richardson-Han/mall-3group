package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.Topic;
import com.cskaoyan.mall.service.LogService;
import com.cskaoyan.mall.service.TopicService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 韩
 * @create 2020-06-27 1:08
 */
@RestController
@RequestMapping("admin/topic")
public class TopicController {
    @Autowired
    TopicService topicService;

    @Autowired
    LogService logService;

    @RequiresAuthentication
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseRespVo list(Integer page, Integer limit, String sort, String order) {
        BaseData baseData = topicService.queryTopic(page, limit, sort, order);
        return BaseRespVo.ok(baseData);
    }

    /**
     * 创建
     * 因网页未提供updatetime、addtime 需自行注入（service）
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public BaseRespVo create(@RequestBody Topic topic) {
        Integer insert = topicService.createTopic(topic);
        if (insert == 1) {
            topic.setGoods("[]");
            topic.setId(topicService.selectLastId());
            String username = (String) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
            String operationName = "专题";
            logService.setAdminCreate(username,operationName,topic.getId());
            return BaseRespVo.ok(topic);
        } else {
            return BaseRespVo.error("创建失败", 404);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public BaseRespVo update(@RequestBody Topic topic) {
        Integer update = topicService.updateTopic(topic);
        if (update == 1) {
            String username = (String) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
            String operationName = "专题";
            logService.setUpdate(username,topic.getTitle(),operationName);
            return BaseRespVo.ok(topic);
        } else {
            return BaseRespVo.error("修改失败", 999);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseRespVo delete(@RequestBody Topic topic) {
        Integer insert = topicService.deleteTopic(topic);
        if (insert == 1) {
            String username = (String) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
            logService.deleteAdmin(username,topic.getId());
            return BaseRespVo.ok();
        } else {
            return BaseRespVo.error("删除失败", 404);
        }

    }
}

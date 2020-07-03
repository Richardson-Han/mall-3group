package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Topic;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.TopicService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* *
@author  Walker-胡
@create 2020-07-01 16:27
*/
@RestController
@RequestMapping("/wx/topic")
@RequiresAuthentication
public class WXTopicController {
    @Autowired
    TopicService topicService;

    /*
     * 微信小程序显示专题，未对返回的数据进行封装，
     * 如需封装，建议将得到的iteam遍历，将Topic对象改成WXTopicVO对象
     * */
    @GetMapping("/list")
    public BaseRespVo getTopicList(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Map<String, Object> map = new HashMap<>();
        String order = "desc";
        String sort = "add_time";
        BaseData baseData = topicService.queryTopic(page, size, sort, order);
        map.put("count", baseData.getTotal());
        map.put("data", baseData.getItems());
        return BaseRespVo.ok(map);
    }

    /*
     * 微信小程序显示专题细节
     * */
    @GetMapping("/detail")
    public BaseRespVo getTopicDetail(@RequestParam("id") Integer id) {
        Map<String, Object> map = topicService.selectDetail(id);
        return BaseRespVo.ok(map);

    }

    /*
     * 微信小程序显示专题细节相关内容
     * */
    @GetMapping("/related")
    public BaseRespVo getTopicDetailRelated(@RequestParam("id") Integer id) {
        List<Topic> list = topicService.selectRelated(id);
        return BaseRespVo.ok(list);

    }
}



package com.cskaoyan.mall.service;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Topic;

import java.util.List;

import java.util.Map;



/**
 * @author 韩
 * @create 2020-06-27 1:09
 */
public interface TopicService {

    BaseData queryTopic(Integer page, Integer limit, String sort, String order);

    Integer createTopic(Topic topic);

    Integer updateTopic(Topic topic);

    Integer deleteTopic(Topic topic);

    List<Topic> wxselectNewTopic();

    Integer selectLastId();

    //用于小程序查询专题细节
    Map<String, Object> selectDetail(Integer id);
   // 用于小程序查询相关的专题
    List<Topic> selectRelated(Integer id);
}

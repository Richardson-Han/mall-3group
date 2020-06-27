package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Topic;

/**
 * @create 2020-06-27 1:09
 */
public interface TopicService {

    BaseData queryTopic(Integer page, Integer limit, String sort, String order);

    Integer createTopic(Topic topic);

    Integer updateTopic(Topic topic);

    Integer deleteTopic(Topic topic);
}

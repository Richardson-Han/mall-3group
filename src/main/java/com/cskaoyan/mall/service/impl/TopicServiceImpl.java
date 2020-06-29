package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Topic;
import com.cskaoyan.mall.bean.TopicExample;
import com.cskaoyan.mall.mapper.TopicMapper;
import com.cskaoyan.mall.service.TopicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 韩
 * @create 2020-06-27 1:10
 */
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicMapper topicMapper;

    @Override
    public BaseData queryTopic(Integer page, Integer limit, String sort, String order) {
        TopicExample topicExample = new TopicExample();
        topicExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        List<Topic> topics = topicMapper.selectByExample(topicExample);
        PageInfo<Topic> pageInfo = new PageInfo<>(topics);
        long total = pageInfo.getTotal();
        return new BaseData(topics, total);

    }

    /**
     * 创建
     */
    @Override
    public Integer createTopic(Topic topic) {
        topic.setAddTime(new Date());
        topic.setUpdateTime(new Date());
        int insert = topicMapper.insertSelective(topic);
        return insert;
    }

    @Override
    public Integer updateTopic(Topic topic) {
        int update = topicMapper.updateByPrimaryKey(topic);
        return update;
    }

    /**
     * 需要先手动设置更新事件和deleted列的值为true
     */
    @Override
    public Integer deleteTopic(Topic topic) {
        topic.setUpdateTime(new Date());
        topic.setDeleted(true);
        int update = topicMapper.updateByPrimaryKeySelective(topic);
        return update;
    }
}

package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Feedback;
import com.cskaoyan.mall.bean.FeedbackExample;
import com.cskaoyan.mall.mapper.FeedbackMapper;
import com.cskaoyan.mall.service.FeedbackService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @author 社会主义好
 * @date 2020-06-27 9:10 星期六 
 *
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackMapper feedbackMapper;

    @Override
    public BaseData queryFeedback(Integer page, Integer limit, String sort, String order, String username, Integer id) {

        FeedbackExample feedbackExample = new FeedbackExample();
        FeedbackExample.Criteria criteria = feedbackExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (username != null && !username.isEmpty()) {
            //criteria.andUsernameEqualTo(username);
            criteria.andUsernameLike(username);
        }
        if (id != null) {
            criteria.andIdEqualTo(id);
        }
        feedbackExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        List<Feedback> feedbacks = feedbackMapper.selectByExample(feedbackExample);
        PageInfo<Feedback> feedbackPageInfo = new PageInfo<>(feedbacks);
        long total = feedbackPageInfo.getTotal();
        return new BaseData(feedbacks, total);
    }
}

package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.bean.UserExample;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public BaseData queryUsers(Integer page, Integer limit, String sort, String order) {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause(sort + " " + order);
        //如果你当前的查询有条件，就增加criteria
        //userExample.createCriteria().andAddTimeBetween()
        //执行查询之前使用分页
        PageHelper.startPage(page,limit);
        List<User> users = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        long total = pageInfo.getTotal();
        return new BaseData(users, total);
    }
}
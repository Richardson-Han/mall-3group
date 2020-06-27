package com.cskaoyan.mall.service.impl.szyIml;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.bean.UserExample;
import com.cskaoyan.mall.mapper.szyMapper.UserMapper;
import com.cskaoyan.mall.service.szyService.UserService;
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
    public BaseData queryUsers(Integer page, Integer limit, String sort, String order, String username, String mobile) {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause(sort + " " + order);
        //如果你当前的查询有条件，就增加criteria
        //userExample.createCriteria().andAddTimeBetween()
        //执行查询之前使用分页
        UserExample.Criteria criteria = userExample.createCriteria();
        if (username != null && !username.isEmpty()) {
            criteria.andUsernameEqualTo(username);
        }
        if (mobile != null &&!mobile.isEmpty()) {
            criteria.andMobileEqualTo(mobile);
        }
        PageHelper.startPage(page,limit);
        List<User> users = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        long total = pageInfo.getTotal();
        return new BaseData(users, total);
    }

    /**
     *
     * @return 首页返回用户数量
     */
    @Override
    public Long getUserTotal() {
        return userMapper.countByExample(new UserExample());
    }
}

package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.VO.StatBaseVO;
import com.cskaoyan.mall.bean.VO.wx.WXUserInfoVO;
import com.cskaoyan.mall.mapper.CouponMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    CouponMapper couponMapper;

    @Override
    public BaseData queryUsers(Integer page, Integer limit, String sort, String order, String username, String mobile) {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause(sort + " " + order);
        //如果你当前的查询有条件，就增加criteria
        //userExample.createCriteria().andAddTimeBetween()
        //执行查询之前使用分页
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (username != null && !username.isEmpty()) {
            //criteria.andUsernameEqualTo(username);
            criteria.andUsernameLike("%" + username + "%");
        }
        if (mobile != null && !mobile.isEmpty()) {
            //criteria.andMobileEqualTo(mobile);
            criteria.andMobileLike("%" + mobile + "%");
        }
        PageHelper.startPage(page, limit);
        List<User> users = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        long total = pageInfo.getTotal();
        return new BaseData(users, total);
    }

    /**
     * @return 首页返回用户数量
     */
    @Override
    public Long getUserTotal() {
        return userMapper.countByExample(new UserExample());
    }

    /**
     * 获取每天新增的用户
     *
     * @return
     */
    @Override
    public StatBaseVO getUserStat() {

        StatBaseVO statUserVO = new StatBaseVO();
        ArrayList<String> columns = new ArrayList<>();
        columns.add("day");
        columns.add("users");
        statUserVO.setColumns(columns);

        List<UserStat> userStats = userMapper.selectGroupByAddTime();
        statUserVO.setRows(userStats);
        return statUserVO;
    }

    @Override
    public Integer wxselectIdByUsername(String username) {
        return userMapper.selectIdByUsername(username);
    }

    @Override
    public Integer wxinsertGainCoupon(Integer userId, Integer couponId, Integer total) {
        Date addtime = new Date();
        Integer insert = userMapper.wxinsertCouponByUseridAndCouponid(userId, couponId, addtime);
        if (insert == 1 && total != 0) {
            total--;
            userMapper.wxupdateCouponByCouponId(couponId, total);
            //当total从1变成0时
            if (total == 0) {
                //虚拟删除
                userMapper.wxdeleteByCouponId(couponId);
                //变更total为-1避免为0时不安全，被视作无限量
                total = -1;
                userMapper.wxupdateCouponByCouponId(couponId, total);
            }
        }

        return insert;
    }

    @Override
    public WXUserInfoVO getUserInfo(String username) {
        return userMapper.selectUserInfoByUsername(username);
    }
}

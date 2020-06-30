package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.wx.CouponBase;
import com.cskaoyan.mall.mapper.CouponMapper;
import com.cskaoyan.mall.mapper.CouponUserMapper;
import com.cskaoyan.mall.service.CouponService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.System;
import java.util.Date;
import java.util.List;

/**
 * @author 韩
 * @create 2020-06-27 0:27
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    CouponUserMapper couponUserMapper;


    @Override
    public BaseData queryCoupon(Integer page, Integer limit, String sort, String order) {
        CouponExample couponExample = new CouponExample();
        couponExample.setOrderByClause(sort + " " + order);
        //执行查询之前使用分页
        PageHelper.startPage(page, limit);
        List<Coupon> coupons = couponMapper.selectByExample(couponExample);
        PageInfo<Coupon> pageInfo = new PageInfo<>(coupons);
        long total = pageInfo.getTotal();
        return new BaseData(coupons, total);
    }

    @Override
    public Coupon readCoupon(Integer id) {
        Coupon coupon = couponMapper.selectByPrimaryKey(id);
        return coupon;
    }

    @Override
    public Integer createCoupon(Coupon coupon) {
        coupon.setAddTime(new Date());
        coupon.setUpdateTime(new Date());
        int insert = couponMapper.insertSelective(coupon);
        return insert;
    }

    @Override
    public Integer updateCoupon(Coupon coupon) {
        int update = couponMapper.updateByPrimaryKeySelective(coupon);
        return update;
    }

    /**
     * 虚拟删除，注入更新事件和被删除状态
     */
    @Override
    public Integer deleteCoupon(Coupon coupon) {
        coupon.setDeleted(true);
        coupon.setUpdateTime(new Date());
        int deleteCoupon = couponMapper.updateByPrimaryKeySelective(coupon);
        return deleteCoupon;
    }

    @Override
    public BaseData listuserCouponUser(Integer page, Integer limit,
                                       Integer couponId, String sort, String order) {
        CouponExample couponExample = new CouponExample();
        couponExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        List<CouponUser> couponUsers = couponUserMapper.listuserCouponUser(couponId);
        PageInfo<CouponUser> pageInfo = new PageInfo<>(couponUsers);
        long total = pageInfo.getTotal();
        return new BaseData(couponUsers, total);
    }


    @Override
    public CouponBase WXlistCoupon(Integer page, Integer size) {
        Integer count= couponMapper.selectCountNumber();
        List<Coupon> coupons = couponMapper.selectAllCoupon(page, size);
        CouponBase data = new CouponBase();
        data.setData(coupons);
        data.setCount(count);
        return data;
    }

    @Override
    public BaseData listuserUserIdCouponUser(Integer page, Integer limit, Integer couponId,
                                             Integer userId, String sort, String order) {
        CouponExample couponExample = new CouponExample();
        couponExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        List<CouponUser> couponUsers = couponUserMapper.listuserUserIdCouponUser(userId,couponId);
        PageInfo<CouponUser> pageInfo = new PageInfo<>(couponUsers);
        long total = pageInfo.getTotal();
        return new BaseData(couponUsers,total);
    }
}

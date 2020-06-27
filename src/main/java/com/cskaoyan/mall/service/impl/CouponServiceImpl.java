package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.CouponMapper;
import com.cskaoyan.mall.service.CouponService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @create 2020-06-27 0:27
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponMapper couponMapper;


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
    public BaseData listuserCoupon(Integer page, Integer limit, Integer couponId, String sort, String order) {
        CouponExample couponExample = new CouponExample();
        couponExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        // List<Coupon> coupons = couponMapper.selectByExample()
        return new BaseData();
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
}

package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.mapper.GroupOnRulesMapper;
import com.cskaoyan.mall.service.GroupService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * 团购管理业务层
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupOnRulesMapper groupOnRulesMapper;

    @Autowired
    GoodsMapper goodsMapper;
    /**
     * 从cskaoyanmall_groupon_rules表中获取团购规则的数据
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @Override
    public BaseData queryGroupOnRules(Integer page, Integer limit, String sort, String order) {
        GroupOnRulesExample groupOnRulesExample = new GroupOnRulesExample();
        groupOnRulesExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page,limit);
        List<GroupOnRules> groupOnRules = groupOnRulesMapper.selectByExample(groupOnRulesExample);
        PageInfo<GroupOnRules> pageInfo = new PageInfo<>(groupOnRules);
        long total = pageInfo.getTotal();
        return new BaseData(groupOnRules,total);
    }

    /**
     * 编辑团购商品信息
     * 先查询goods表中是否存在此商品
     * 若存在则更新groupon_rules表中的数据
     * @param groupOnRules
     * @return
     */
    @Override
    public Boolean update(GroupOnRules groupOnRules) {
        //根据goodsId查询goods表中是否存在此商品
        int goods = goodsMapper.isGoodsExit( groupOnRules.getGoodsId());
        if(goods == 0){
            return false;
        }

        //更新groupon_rules表中的数据
        int i = groupOnRulesMapper.updateByPrimaryKey(groupOnRules);
        return i==1;
    }

    /**
     * 新建团购，往groupon_rules表中添加数据
     * 需判断时间和商品id是否合法
     * 合法则查询商品相应信息
     * 添加数据
     * @param groupOnRules
     * @return
     */
    @Override
    public BaseRespVo create(GroupOnRules groupOnRules) {
        //获取当前系统时间
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//        String updateTime = simpleDateFormat.format(new Date());
        Date updateTime = new Date();
        //校验
        int goodsExit = goodsMapper.isGoodsExit(groupOnRules.getGoodsId());
        if(updateTime.after(groupOnRules.getExpireTime()) && goodsExit == 0){
            return BaseRespVo.err("参数值不对",402);
        }

        //查询商品信息
        Goods goods = goodsMapper.selectByPrimaryKey(groupOnRules.getGoodsId());
        //添加数据
        groupOnRules.setGoodsName(goods.getName());
        groupOnRules.setPicUrl(goods.getPicUrl());
        groupOnRules.setAddTime(updateTime);
        groupOnRules.setUpdateTime(updateTime);
        groupOnRules.setDeleted(false);
        if(groupOnRulesMapper.insert(groupOnRules) != 1){
            return BaseRespVo.err("参数值不对",402);
        }
        //查询刚插入的数据的id
        Integer id = groupOnRulesMapper.selectLastInsertId();
        groupOnRules.setId(id);
        return BaseRespVo.ok(groupOnRules);
    }

    /**
     * 删除团购信息
     * 根据id删除groupon_rules表中数据，
     * @param groupOnRules
     * @return
     */
    @Override
    public Integer delete(GroupOnRules groupOnRules) {
        
        return null;
    }
}

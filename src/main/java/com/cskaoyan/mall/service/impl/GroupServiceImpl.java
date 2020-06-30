package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.VO.BaseRespVo;

import com.cskaoyan.mall.bean.VO.GroupOnListRecordVO;
import com.cskaoyan.mall.bean.VO.IDsVO;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.mapper.GroupOnMapper;
import com.cskaoyan.mall.mapper.GroupOnRulesMapper;
import com.cskaoyan.mall.service.GroupService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 团购管理业务层
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupOnRulesMapper groupOnRulesMapper;

    @Autowired
    GroupOnMapper groupOnMapper;

    @Autowired
    GoodsMapper goodsMapper;
    /**
     * 从cskaoyanmall_groupon_rules表中获取团购规则的数据
     * 如果有goodsId则按goodsId获取
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @Override
    public BaseData queryGroupOnRules(Integer page, Integer limit, String sort, String order, Integer goodsId) {
        GroupOnRulesExample groupOnRulesExample = new GroupOnRulesExample();
        groupOnRulesExample.setOrderByClause(sort + " " + order);
        //在按商品id搜索时有goodsId
        if(goodsId != null){
            groupOnRulesExample.createCriteria().andGoodsIdEqualTo(goodsId);
        }
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
            return BaseRespVo.error("参数值不对",402);
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
            return BaseRespVo.error("参数值不对",402);
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
        int result = groupOnRulesMapper.deleteByPrimaryKey(groupOnRules.getId());
        return result;
    }

    /**
     * 查询团购活动
     * 获取cskaoyanmall_groupon表中的数据
     * 获取cskaoyanmall_groupon_rules表中的数据
     * 获取cskaoyanmall_goods表中的数据
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param goodsId
     * @return
     */
    @Override
    public BaseData listRecord(Integer page, Integer limit, String sort, String order, Integer goodsId) {
        GroupOnExample groupOnExample = new GroupOnExample();
        groupOnExample.setOrderByClause(sort + " " + order);
        GroupOnRulesExample groupOnRulesExample = new GroupOnRulesExample();
        groupOnRulesExample.setOrderByClause(sort + " " + order);
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.setOrderByClause(sort + " " + order);
        //在按商品id搜索时有goodsId
//        if(goodsId != null){
//            groupOnExample.createCriteria().andGoodsIdEqualTo(goodsId);
//        }
        PageHelper.startPage(page,limit);
        List<GroupOn> groupOns = groupOnMapper.selectByExample(groupOnExample);
        List<GroupOnListRecordVO> groupOnListRecordVOs = new ArrayList<>();
        for (GroupOn groupOn : groupOns) {

            GroupOnListRecordVO recordVO = new GroupOnListRecordVO();
            GroupOnRules rules = groupOnRulesMapper.selectByPrimaryKey(groupOn.getRulesId());
            Goods goods = goodsMapper.selectByPrimaryKey(rules.getGoodsId());
            recordVO.setRules(rules);
            recordVO.setGoods(goods);
            recordVO.setGroupon(groupOn);

            List<IDsVO> iDsVOList = groupOnMapper.selectOrderIdAndUserIdByGrouponId(groupOn.getGrouponId());
            recordVO.setSubGroupons(iDsVOList);

            groupOnListRecordVOs.add(recordVO);
            }

        PageInfo<GroupOnListRecordVO> pageInfo = new PageInfo<>(groupOnListRecordVOs);
        long total = pageInfo.getTotal();
        return new BaseData(groupOnListRecordVOs,total);
    }
}

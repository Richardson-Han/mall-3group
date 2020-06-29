package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.GroupOnRules;
import com.cskaoyan.mall.bean.GroupOnRulesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupOnRulesMapper {
    /**
     * 用于查询最后插入数据的自增id
     * @return
     */
    Integer selectLastInsertId();

    long countByExample(GroupOnRulesExample example);

    int deleteByExample(GroupOnRulesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GroupOnRules record);

    int insertSelective(GroupOnRules record);

    List<GroupOnRules> selectByExample(GroupOnRulesExample example);

    GroupOnRules selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GroupOnRules record, @Param("example") GroupOnRulesExample example);

    int updateByExample(@Param("record") GroupOnRules record, @Param("example") GroupOnRulesExample example);

    int updateByPrimaryKeySelective(GroupOnRules record);

    int updateByPrimaryKey(GroupOnRules record);
}
package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.GroupOnRules;
import com.cskaoyan.mall.bean.GroupOnRulesExample;
import com.cskaoyan.mall.bean.wx.GroupBuy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("select (g.retail_price - gr.discount) as groupon_price,g.id as `goods.id`,g.name as `goods.name`,g.brief as `goods.brief`,g.pic_url as `gooods.picUrl`,g.counter_price as `goods.counterPrice`,g.retail_price as `goods.retailPrice`,gr.discount_member as groupon_member from cskaoyanmall_groupon_rules gr left join cskaoyanmall_goods g on gr.goods_id = g.id order by g.id desc limit 0,5;")
    List<GroupBuy> selectGroupBuy();

}
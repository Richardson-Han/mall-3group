package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.GroupOn;
import com.cskaoyan.mall.bean.GroupOnExample;
import com.cskaoyan.mall.bean.VO.IDsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupOnMapper {
    long countByExample(GroupOnExample example);

    int deleteByExample(GroupOnExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GroupOn record);

    int insertSelective(GroupOn record);

    List<GroupOn> selectByExample(GroupOnExample example);

    GroupOn selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GroupOn record, @Param("example") GroupOnExample example);

    int updateByExample(@Param("record") GroupOn record, @Param("example") GroupOnExample example);

    int updateByPrimaryKeySelective(GroupOn record);

    int updateByPrimaryKey(GroupOn record);

    List<IDsVO> selectOrderIdAndUserIdByGrouponId(@Param("grouponId") Integer grouponId);

    int selectByOrderId(@Param("id") Integer id);
}
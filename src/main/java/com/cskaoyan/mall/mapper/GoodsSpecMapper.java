package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.GoodsSpec;
import com.cskaoyan.mall.bean.GoodsSpecExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsSpecMapper {
    long countByExample(GoodsSpecExample example);

    int deleteByExample(GoodsSpecExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsSpec record);

    int insertSelective(GoodsSpec record);

    List<GoodsSpec> selectByExample(GoodsSpecExample example);

    GoodsSpec selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsSpec record, @Param("example") GoodsSpecExample example);

    int updateByExample(@Param("record") GoodsSpec record, @Param("example") GoodsSpecExample example);

    int updateByPrimaryKeySelective(GoodsSpec record);

    int updateByPrimaryKey(GoodsSpec record);
}
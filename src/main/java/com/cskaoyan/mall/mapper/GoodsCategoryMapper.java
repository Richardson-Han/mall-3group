package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.VO.CatChildrenVO;
import com.cskaoyan.mall.bean.GoodsCategory;
import com.cskaoyan.mall.bean.GoodsCategoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsCategoryMapper {
    long countByExample(GoodsCategoryExample example);

    int deleteByExample(GoodsCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsCategory record);

    int insertSelective(GoodsCategory record);

    List<GoodsCategory> selectByExample(GoodsCategoryExample example);

    List<CatChildrenVO> selectCustom(@Param("value") Integer value);

    GoodsCategory selectByPrimaryKey(Integer id);


    int updateByExampleSelective(@Param("record") GoodsCategory record, @Param("example") GoodsCategoryExample example);

    int updateByExample(@Param("record") GoodsCategory record, @Param("example") GoodsCategoryExample example);

    int updateByPrimaryKeySelective(GoodsCategory record);

    int updateByPrimaryKey(GoodsCategory record);
}
package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.BrandBO;
import com.cskaoyan.mall.bean.GoodsBrand;
import com.cskaoyan.mall.bean.GoodsBrandExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsBrandMapper {
    long countByExample(GoodsBrandExample example);

    int deleteByExample(GoodsBrandExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsBrand record);

    int insertSelective(GoodsBrand record);

    List<GoodsBrand> selectByExample(GoodsBrandExample example);

    //只获取对应的id和name
    List<BrandBO> selectCustom(GoodsBrandExample example);

    GoodsBrand selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsBrand record, @Param("example") GoodsBrandExample example);

    int updateByExample(@Param("record") GoodsBrand record, @Param("example") GoodsBrandExample example);

    int updateByPrimaryKeySelective(GoodsBrand record);

    int updateByPrimaryKey(GoodsBrand record);
}
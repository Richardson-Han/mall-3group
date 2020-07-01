package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.KeyWord;
import com.cskaoyan.mall.bean.KeyWordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KeyWordMapper {
    long countByExample(KeyWordExample example);

    int deleteByExample(KeyWordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(KeyWord record);

    int insertSelective(KeyWord record);

    List<KeyWord> selectByExample(KeyWordExample example);

    KeyWord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") KeyWord record, @Param("example") KeyWordExample example);

    int updateByExample(@Param("record") KeyWord record, @Param("example") KeyWordExample example);

    int updateByPrimaryKeySelective(KeyWord record);

    int updateByPrimaryKey(KeyWord record);

    KeyWord selectByIsDefaultIdSortOrder();
}
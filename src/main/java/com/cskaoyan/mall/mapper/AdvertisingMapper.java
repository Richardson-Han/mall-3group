package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Advertising;
import com.cskaoyan.mall.bean.AdvertisingExample;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

public interface AdvertisingMapper {
    long countByExample(AdvertisingExample example);

    int deleteByExample(AdvertisingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Advertising record);

    int insertSelective(Advertising record);

    List<Advertising> selectByExample(AdvertisingExample example);

    Advertising selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Advertising record, @Param("example") AdvertisingExample example);

    int updateByExample(@Param("record") Advertising record, @Param("example") AdvertisingExample example);

    int updateByPrimaryKeySelective(Advertising record);

    int updateByPrimaryKey(Advertising record);
}
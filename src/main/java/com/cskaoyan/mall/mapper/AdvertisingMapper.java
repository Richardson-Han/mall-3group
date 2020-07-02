package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Advertising;
import com.cskaoyan.mall.bean.AdvertisingExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("select id,name,link,url,`position`,content,enabled,add_Time as `addTime`," +
            "update_time as updateTime,deleted from cskaoyanmall_ad limit 0,10")
    List<Advertising> selectTopAdvertising();

    @Select("select LAST_INSERT_ID()")
    Integer selectLastAdvertisingId();

    @Select("select id,name,url,position,content,enabled,add_time as addTime,update_time as updateTime " +
            "from cskaoyanmall_ad where id = #{id}")
    Advertising selectLastAdvertising(@Param("id") Integer id);
}
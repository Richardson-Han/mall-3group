package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Footprint;
import com.cskaoyan.mall.bean.FootprintExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface FootprintMapper {
    long countByExample(FootprintExample example);

    int deleteByExample(FootprintExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Footprint record);

    int insertSelective(Footprint record);

    List<Footprint> selectByExample(FootprintExample example);

    Footprint selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Footprint record, @Param("example") FootprintExample example);

    int updateByExample(@Param("record") Footprint record, @Param("example") FootprintExample example);

    int updateByPrimaryKeySelective(Footprint record);

    int updateByPrimaryKey(Footprint record);

    List<Footprint> queryFootprintList();

    void updateTime(@Param("date") Date date, @Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    @Select("select g.brief,g.pic_url as picUrl,f.add_time as `addTime`,f.goods_id as goodsId," +
            "g.name,f.id,g.retail_price as retailPrice from cskaoyanmall_footprint f,cskaoyanmall_goods g \n" +
            "where f.user_id = #{userId} and f.`goods_id`=g.`id` order by f.add_time desc limit #{page},#{size}")
    List<Footprint> selectlistByPageAndSizeAndUserid(@Param("page") Integer page, @Param("size") Integer size,
                                                     @Param("userId") Integer userId);
}
package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.bean.BrandExample;
import com.cskaoyan.mall.bean.VO.wx.BrandGetListVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BrandMapper {
    long countByExample(BrandExample example);

    int deleteByExample(BrandExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Brand record);

    int insertSelective(Brand record);

    List<Brand> selectByExample(BrandExample example);
    List<Brand> queryBrandPageList(@Param ("id") Integer id,
                                   @Param ("name") String name,
                                   @Param ("sort1") String sort1,
                                   @Param ("order1") String order1);

    Brand selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Brand record, @Param("example") BrandExample example);

    int updateByExample(@Param("record") Brand record, @Param("example") BrandExample example);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKey(Brand record);

    @Select("select id,name,`desc`,pic_url as picUrl,floor_price as floorPrice " +
            "from cskaoyanmall_brand order by id desc limit 0,4")
    List<Brand> selectDirectSupply();

    @Select("select id,`name`,`desc`,pic_url as picUrl,floor_price as floorPrice from cskaoyanmall_brand")
    List<BrandGetListVO> selectBrandListVO();
}
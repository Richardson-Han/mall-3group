package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Address;
import com.cskaoyan.mall.bean.AddressExample;
import com.cskaoyan.mall.bean.VO.wx.DetailFromAddress;
import com.cskaoyan.mall.bean.VO.wx.WXAddressListVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AddressMapper {
    long countByExample(AddressExample example);

    int deleteByExample(AddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Address record);

    int insertSelective(Address record);

    List<Address> selectByExample(AddressExample example);

    Address selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Address record, @Param("example") AddressExample example);

    int updateByExample(@Param("record") Address record, @Param("example") AddressExample example);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    List<Address> selectByOrder(String sort, String order);

    @Select("SELECT a.id,`name`,address as detailedAddress,a.mobile,is_default as isDefault \n" +
            "FROM cskaoyanmall_address a \n" +
            "left join cskaoyanmall_user u on a.user_id = u.id \n" +
            "where username = #{username}")
    List<WXAddressListVO> selectAddressByUsername(@Param("username") String username);

    @Select("select id,`name`,province_id as provinceId,city_id as cityId," +
            "area_id as areaId,address,is_default as isDefault,mobile " +
            "from cskaoyanmall_address where id = #{id}")
    DetailFromAddress selectDetaileById(@Param("id") Integer id);

    @Select("select `name` as provinceName from cskaoyanmall_region where id = #{id}")
    String selectProvinceById(@Param("id") Integer provinceId);

    @Select("select `name` as cityName from cskaoyanmall_region where id = #{id}")
    String selectCityById(@Param("id") Integer cityId);

    @Select("select `name` as areaName from cskaoyanmall_region where id = #{id}")
    String selectAreaById(@Param("id") Integer cityId);

    Integer getLastUpdateId();
}
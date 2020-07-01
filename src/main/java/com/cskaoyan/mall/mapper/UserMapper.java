package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.bean.UserExample;
import com.cskaoyan.mall.bean.UserStat;
import com.cskaoyan.mall.bean.VO.wx.WXUserInfoVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<UserStat> selectGroupByAddTime();

    @Update("update cskaoyanmall_user set password = #{password} where username = #{username}")
    Integer updatePasswordByUsername(@Param("username") String username, @Param("password") String passwordDB);

    @Select("select id from cskaoyanmall_user where username = #{username}")
    Integer selectIdByUsername(@Param("username") String username);

    Integer selectUserIdByUsername(@Param("username") String username);

    @Insert("insert into cskaoyanmall_coupon_user(user_id,coupon_id,add_time) values (#{userId},#{couponId},#{addtime})")
    Integer wxinsertCouponByUseridAndCouponid(@Param("userId") Integer userId, @Param("couponId") Integer couponId, @Param("addtime") Date addtime);

    @Update("update cskaoyanmall_coupon set total=#{total} where coupon_id = #{couponId}")
    Integer wxupdateCouponByCouponId(@Param("couponId") Integer couponId, @Param("total") Integer total);

    @Update("update cskaoyanmall_coupon set deleted = 1 where coupon_id = #{couponId}")
    void wxdeleteByCouponId(@Param("couponId") Integer couponId);

    @Select("select nickname,avatar as avatarUrl from cskaoyanmall_user where username = #{username}")
    WXUserInfoVO selectUserInfoByUsername(String username);

    @Select("select password as strings from cskaoyanmall_user where username = #{username}")
    List<String> selectPasswordByName(String username);
}
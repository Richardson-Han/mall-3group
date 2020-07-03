package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Coupon;
import com.cskaoyan.mall.bean.CouponExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

public interface CouponMapper {
    long countByExample(CouponExample example);

    int deleteByExample(CouponExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    List<Coupon> selectByExample(CouponExample example);

    List<Coupon> selectByExampleFang(CouponExample example);

    Coupon selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Coupon record, @Param("example") CouponExample example);

    int updateByExample(@Param("record") Coupon record, @Param("example") CouponExample example);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    @Select("select id,`name`,`desc`,tag,discount,`min`,days from cskaoyanmall_coupon order by id desc limit 0,3")
    List<Coupon> selectNewCoupons();

    @Select("select id,`name`,`desc`,tag,discount ,`min`,days " +
            "from cskaoyanmall_coupon where deleted = 0 order by id desc limit #{page}, #{size}")
    List<Coupon> selectAllCoupon(@Param("page") Integer page, @Param("size") Integer size);

    @Select("select COUNT(*) from cskaoyanmall_coupon where deleted = 0")
    Integer selectCountNumber();

    @Select("select id from cskaoyanmall_coupon where `code` = #{code}")
    Integer selectIdByCode(@Param("code") String code);

    @Select("select `limit` from cskaoyanmall_coupon where id = #{couponId}")
    Integer selectlimitById(@Param("couponId") Integer couponId);

    @Select("select COUNT(*) from cskaoyanmall_coupon_user where coupon_id = #{couponId} and user_id = #{userId}")
    Integer selectCountByUseridAndCouponid(@Param("userId") Integer userId, @Param("couponId") Integer couponId);

    @Select("select total from cskaoyanmall_coupon where id = #{couponId} and deleted = 0")
    Integer wxselectTotalByCouponId(@Param("couponId") Integer couponId);

    @Select("select cc.id,cc.name,cc.desc,cc.tag,cc.min,cc.discount,cc.start_time as startTime,cc.end_time as endTime " +
            "from cskaoyanmall_coupon cc,(select coupon_id from `cskaoyanmall_coupon_user` where  user_id = #{userId}) t " +
            "where cc.id =t.coupon_id and cc.status=#{status} limit #{page},#{size}")
    List<Coupon> wxselectCouponByStatusPage(@Param("status") Integer status, @Param("page") Integer page,
                                            @Param("size") Integer size, @Param("userId") Integer userId);

    @Select("select COUNT(*) from cskaoyanmall_coupon cc,(select coupon_id from `cskaoyanmall_coupon_user` " +
            "where  user_id = #{userId}) t where cc.id =t.coupon_id and cc.status= #{status}")
    Integer selectStatusCount(@Param("status") Integer status, @Param("userId") Integer userId);

    @Select("select c.id,c.name,c.desc,c.tag,c.min,c.discount,c.start_time as startTime,c.end_time as endTime " +
            "from `cskaoyanmall_coupon` c,(select coupon_id from`cskaoyanmall_coupon_user` where user_id = #{userId} " +
            "and deleted = 0 and `status` = 0) ci where ci.coupon_id = c.`id`")
    List<Coupon> wxselectCouponByUserId(@Param("userId") Integer userId);

    @Select("select c.id,c.name,c.desc,c.tag,c.min,c.discount,c.start_time as startTime,c.end_time as endTime " +
            "from `cskaoyanmall_coupon` c," +
            "(select coupon_id from`cskaoyanmall_coupon_user` where user_id = #{userId} and deleted = 0 and `status` = 0) ci," +
            "(select t2.category_id,t1.goods_id from `cskaoyanmall_cart` t1,`cskaoyanmall_goods` t2 where user_id = #{userId} " +
            "and t1.id= #{cartId} and t2.id=t1.`goods_id`) t3 " +
            "where ci.coupon_id = c.`id` and " +
            "(c.`goods_type` = 0 or (c.`goods_type` = 1 and t3.category_id = c.`goods_value`) or " +
            "(c.`goods_type` = 2 and c.`goods_value` = t3.goods_id))")
    List<Coupon> wxselectCouponByCartIdAndUserId(@Param("cartId") Integer cartId, @Param("userId") Integer userId);

    @Select("select c.id,c.name,c.desc,c.tag,c.min,c.discount,c.start_time as startTime,c.end_time as endTime " +
            "from `cskaoyanmall_coupon` c," +
            "(select coupon_id from`cskaoyanmall_coupon_user` where user_id = 3 and deleted = 0 and `status` = 0) ci," +
            "(select t2.category_id,t1.goods_id from `cskaoyanmall_groupon_rules` t1,`cskaoyanmall_goods` t2 " +
            "where user_id = #{userId} and t1.id= #{grouponRulesId} and t2.id=t1.`goods_id`) t3 " +
            "where ci.coupon_id = c.`id` and " +
            "(c.`goods_type` =0 or (c.`goods_type` =1 and t3.category_id = c.`goods_value`) or " +
            "(c.`goods_type`=2 and c.`goods_value` =t3.goods_id))")
    List<Coupon> wxselectCouponByGrouponRulesIdAndUserId(@Param("grouponRulesId") Integer grouponRulesId,
                                                         @Param("userId") Integer userId);

    @Select("select c.id,c.name,c.desc,c.tag,c.min,c.discount,c.start_time as startTime,c.end_time as endTime " +
            "from `cskaoyanmall_coupon` c," +
            "(select coupon_id from`cskaoyanmall_coupon_user` where user_id = 3 and deleted = 0 and `status` = 0) ci," +
            "(select t2.category_id,t1.goods_id from `cskaoyanmall_groupon_rules` t1,`cskaoyanmall_goods` t2 " +
            "where user_id = #{userId} and t1.id= #{grouponRulesId} and t2.id=t1.`goods_id`) t3 " +
            "(select t2.category_id,t1.goods_id from `cskaoyanmall_cart` t4,`cskaoyanmall_goods` t5 " +
            "where user_id = #{userId} and t1.id= #{cartId} and t2.id=t1.`goods_id`) t6 " +
            "where ci.coupon_id = c.`id` and " +
            "(c.`goods_type` = 0 or (c.`goods_type` =1 and " +
            "(t3.category_id = c.`goods_value` or t6.category_id =c.`goods_value`)) " +
            "or(c.`goods_type`=2 and (c.`goods_value` =t3.goods_id or c.`goods_value`=t6.goods_id)))")
    List<Coupon> wxselectCouponByCartIdAndGrouponRulesIdAndUserId(@Param("cartId") Integer cartId,
                                                                  @Param("grouponRulesId") Integer grouponRulesId,
                                                                  @Param("userId") Integer userId);

    @Select("select LAST_INSERT_ID()")
    Integer selectLastId();

    //Fang
    @Select("select discount from cskaoyanmall_coupon where id = #{id}")
    BigDecimal selectDiscountById(@Param("id") Integer id);
}
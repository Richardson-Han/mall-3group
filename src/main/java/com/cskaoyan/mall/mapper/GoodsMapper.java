package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.GoodsExample;
import com.cskaoyan.mall.bean.GoodsStat;
import com.cskaoyan.mall.bean.wx.WXFloorGoods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface GoodsMapper {

    /**
     * 用于根据id判断表中商品是否存在
     * @param id
     * @return
     */
    int isGoodsExit(Integer id);

    long countByExample(GoodsExample example);

    int deleteByExample(GoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    List<Goods> selectByExampleWithBLOBs(GoodsExample example);

    List<Goods> selectByExample(GoodsExample example);

    Goods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExampleWithBLOBs(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExample(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    List<GoodsStat> selectGroupByAddTime();

    @Select("select id,name,brief,pic_url as picUrl,is_new as isNew," +
            "is_hot as isHot,counter_price as counterPrice,retail_price as retailPrice " +
            "from cskaoyanmall_goods order by id desc limit 0,6")
    List<Goods> selectNewgoods();

    @Select("select id,name,brief,pic_url as picUrl,is_new as isNew,is_hot as isHot," +
            "counter_price as counterPrice,retail_price as retailPrice " +
            "from cskaoyanmall_goods where is_hot = 1 order by id desc limit 0,6")
    List<Goods> selectHotGoods();

    @Select("select c.id,c.name from (select a.*,COUNT(*) as num from cskaoyanmall_goods a inner join " +
            "cskaoyanmall_goods b ON a.category_id=b.category_id " +
            "where b.id<=a.id group by a.id having num<=1 order by category_id) g, " +
            "(select * from cskaoyanmall_category order by id) c " +
            "where c.`id`=g.`category_id` and c.id is not null order by g.category_id limit 0,4")
    List<WXFloorGoods> selectCategoryFour();

    @Select("select id,name,brief,pic_url as picUrl,is_new as isNew,is_hot as isHot,counter_price as counterPrice," +
            "retail_price as retailPrice from cskaoyanmall_goods where category_id = #{id} limit 0,4")
    List<Goods> selectByCategoryid(@Param("id") Integer id);

    List<Goods> selectGoodsList(@Param("categoryId") Integer categoryId, @Param("keyword") String keyword, @Param("sort") String sort, @Param("order") String order, @Param("brandId") Integer brandId);

    @Select("select * from cskaoyanmall_goods where id = #{id} and deleted = 0")
    Goods selectByIdAndDeteled(@Param("id") Integer valueId);

    @Select("select LAST_INSERT_ID()")
    Integer selectLastId();

    @Insert("insert into cskaoyanmall_footprint(user_id,goods_id,add_time,update_time) " +
            "values(#{userId},#{id},#{date},#{date})")
    void insertfootprintByUseridAndGoodsid(@Param("userId") Integer userId,
                                           @Param("id") Integer id, @Param("date") Date date);

    @Select("select id from cskaoyanmall_footprint where user_id = #{userId} and goods_id = #{id}")
    Integer selectfootprintByUseridAndGoodsid(@Param("userId") Integer userId, @Param("id") Integer id);

    @Update("update cskaoyanmall_footprint set update_time = #{date} where id = #{oldId} " +
            "and user_id =#{userId} and goods_id = #{id}")
    void updatefootprintByUseridAndGoodsid(@Param("userId") Integer userId, @Param("id") Integer id,
                                           @Param("date") Date date, @Param("oldId") Integer oldId);

    /*@Select("select LAST_INSERT_ID()")
    Integer selectLastId();*/
}
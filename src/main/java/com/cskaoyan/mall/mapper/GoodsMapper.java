package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.GoodsExample;
import com.cskaoyan.mall.bean.GoodsStat;
import com.cskaoyan.mall.bean.wx.FloorGoods;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    List<FloorGoods> selectCategoryFour();

    @Select("select id,name,brief,pic_url as picUrl,is_new as isNew,is_hot as isHot,counter_price as counterPrice," +
            "retail_price as retailPrice from cskaoyanmall_goods where category_id = #{id} limit 0,4")
    List<Goods> selectByCategoryid(@Param("id") Integer id);
}
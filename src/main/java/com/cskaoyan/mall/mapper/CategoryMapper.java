package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.bean.CategoryExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper {
    long countByExample(CategoryExample example);

    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    @Select("SELECT id,NAME,icon_url AS iconUrl from cskaoyanmall_category where pid = 0 order by id limit 0,10")
    List<Category> selectLimitTen();

    @Select("select * from cskaoyanmall_category a left join cskaoyanmall_category b\n" +
            "on a.pid=b.id")
    List<Category> selectFilterCategoryList();

    @Select("select id,name,keywords,`desc`,pid,icon_url as iconUrl,pic_url as picUrl,level," +
            "add_time as addTime,update_time as updateTime,sort_order as sortOrder,deleted " +
            "from cskaoyanmall_category where pid = 0 and deleted = 0")
    List<Category> selectCategoryList();
}
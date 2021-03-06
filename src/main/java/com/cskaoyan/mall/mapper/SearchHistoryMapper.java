package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.SearchHistory;
import com.cskaoyan.mall.bean.SearchHistoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SearchHistoryMapper {
    long countByExample(SearchHistoryExample example);

    int deleteByExample(SearchHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SearchHistory record);

    int insertSelective(SearchHistory record);

    List<SearchHistory> selectByExample(SearchHistoryExample example);

    SearchHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SearchHistory record, @Param("example") SearchHistoryExample example);

    int updateByExample(@Param("record") SearchHistory record, @Param("example") SearchHistoryExample example);

    int updateByPrimaryKeySelective(SearchHistory record);

    int updateByPrimaryKey(@Param("record") SearchHistory record);

    List<SearchHistory> selectKeywordByUsername(@Param("username") String username);

    void deleteByUsername(@Param("username") String username);

    void updateTime(@Param("date") Date date, @Param("userId") Integer userId, @Param("keyword") String keyword);
}
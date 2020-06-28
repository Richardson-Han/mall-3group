package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.SearchHistory;
import com.cskaoyan.mall.bean.SearchHistoryExample;
import com.cskaoyan.mall.mapper.SearchHistoryMapper;
import com.cskaoyan.mall.service.SearchHistoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @author 社会主义好
 * @date 2020-06-27 8:11 星期六 
 *
 */
@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

    @Autowired
    SearchHistoryMapper searchHistoryMapper;

    @Override
    public BaseData queryHistory(Integer page, Integer limit, String sort, String order, Integer userId, String keyword) {
        PageHelper.startPage(page, limit);
        SearchHistoryExample searchHistoryExample = new SearchHistoryExample();
        SearchHistoryExample.Criteria criteria = searchHistoryExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (keyword != null && !keyword.isEmpty()) {
            //criteria.andKeywordEqualTo(keyword);
            criteria.andKeywordLike("%" + keyword + "%");
        }
        searchHistoryExample.setOrderByClause(sort + " " + order);
        List<SearchHistory> searchHistories = searchHistoryMapper.selectByExample(searchHistoryExample);
        PageInfo<SearchHistory> searchHistoryPageInfo = new PageInfo<>(searchHistories);
        long total = searchHistoryPageInfo.getTotal();
        return new BaseData(searchHistories, total);
    }
}

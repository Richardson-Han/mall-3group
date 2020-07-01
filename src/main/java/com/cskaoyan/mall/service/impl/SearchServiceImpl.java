package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.KeyWord;
import com.cskaoyan.mall.bean.KeyWordExample;
import com.cskaoyan.mall.bean.SearchHistory;
import com.cskaoyan.mall.mapper.KeyWordMapper;
import com.cskaoyan.mall.mapper.SearchHistoryMapper;
import com.cskaoyan.mall.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author 社会主义好
 * @date 2020-07-01 9:08 星期三 
 *
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    KeyWordMapper keyWordMapper;

    @Autowired
    SearchHistoryMapper searchHistoryMapper;

    @Override
    public Map<String, Object> index(String username) {

        //搜索栏中的默认关键字显示条件：看keyword表中的isDefault是否为true，如果有多个true，
        //则优先选择sort_order最小的记录，如果sort_order均相同，优先选择id最小的记录
        KeyWord defaultKeyword = keyWordMapper.selectByIsDefaultIdSortOrder();

        //historyKeywordList是和用户相关的
        List<SearchHistory> historyKeywordList = searchHistoryMapper.selectKeywordByUsername(username);

        //hotKeywordList
        KeyWordExample keyWordExample = new KeyWordExample();
        keyWordExample.createCriteria().andIsHotEqualTo(true);
        List<KeyWord> hotKeywordList = keyWordMapper.selectByExample(keyWordExample);
        Map<String, Object> map = new HashMap<>();
        map.put("defaultKeyword", defaultKeyword);
        map.put("historyKeywordList", historyKeywordList);
        map.put("hotKeywordList", hotKeywordList);
        return map;
    }
}

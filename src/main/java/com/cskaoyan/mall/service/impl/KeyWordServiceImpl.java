package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.KeyWordMapper;
import com.cskaoyan.mall.service.KeyWordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class KeyWordServiceImpl implements KeyWordService {
    @Autowired
    KeyWordMapper keyWordMapper;

    @Override
    public BaseData queryKeyWord(KeyWordBO keyWordBO) {
        Integer page = keyWordBO.getPage();
        Integer limit = keyWordBO.getLimit();
        String sort = keyWordBO.getSort();
        String order = keyWordBO.getOrder();
        KeyWordExample keyWordExample = new KeyWordExample();
        keyWordExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        List<KeyWord> keyWords = new ArrayList<>();
        long total = 0;
        String keyword = keyWordBO.getKeyword();
        String url = keyWordBO.getUrl();
        //分情况
        if(keyword != null && url == null){
            keyWordExample.createCriteria().andKeywordLike("%" + keyword + "%");
        }else if(keyword == null && url != null){
            keyWordExample.createCriteria().andUrlLike("%" + url + "%");
        }else if(keyword !=null && url != null){
            keyWordExample.createCriteria().andKeywordLike("%" + keyword + "%").andUrlLike("%" + url + "%");
        }
        //注意deleted
        keyWordExample.createCriteria().andDeletedEqualTo(false);
        keyWords = keyWordMapper.selectByExample(keyWordExample);
        PageInfo<KeyWord> keyWordPageInfo = new PageInfo<>(keyWords);
        total = keyWordPageInfo.getTotal();
        return new BaseData(keyWords, total);
    }

    /**
     *  增加keyword
     */
    @Override
    public KeyWordInsertVO createKeyWord(KeyWord keyWord) {
        if(isExist(keyWord)){
            return null;
        }
        keyWord.setId(null);
        keyWord.setSortOrder(null);
        Date date = new Date();
        keyWord.setAddTime(date);
        keyWord.setUpdateTime(date);
        keyWord.setDeleted(false);

        keyWordMapper.insertSelective(keyWord);
        Integer id = keyWord.getId();
        KeyWord keyWord1 = keyWordMapper.selectByPrimaryKey(id);
        KeyWordInsertVO keyWordInsertVO = new KeyWordInsertVO(keyWord1.getId(), keyWord1.getKeyword(), keyWord1.getUrl(), keyWord1.getIsHot(),
                keyWord1.getIsDefault(), keyWord1.getAddTime(), keyWord1.getUpdateTime());
        return keyWordInsertVO;
    }

    /**
     *  修改 keyword信息
     */
    @Override
    public KeyWordInsertVO updateKeyWord(KeyWord keyWord) {
        if(isExist(keyWord)){
            return null;
        }
        keyWord.setSortOrder(0);
        keyWord.setDeleted(false);
        keyWordMapper.updateByPrimaryKeySelective(keyWord);
        KeyWordInsertVO keyWordInsertVO = new KeyWordInsertVO(keyWord.getId(), keyWord.getKeyword(), keyWord.getUrl(), keyWord.getIsHot(),
                keyWord.getIsDefault(), keyWord.getAddTime(), keyWord.getUpdateTime());
        return keyWordInsertVO;
    }

    /**
     *  虚拟删除 keyword
     */
    @Override
    public void deleteKeyWord(KeyWord keyWord) {
        keyWord.setDeleted(true);
        keyWordMapper.updateByPrimaryKeySelective(keyWord);
    }

    /**
     *  判断keyWord是否已存在
     */
    private boolean isExist(KeyWord keyword){
        KeyWordExample keyWordExample = new KeyWordExample();
        List<KeyWord> keyWordList = new ArrayList<>();
        //不用去和deleted为true去比较
        if(keyword.getId() == null){
            //该情况是新增加keyword
            keyWordExample.createCriteria().andKeywordEqualTo(keyword.getKeyword()).andDeletedEqualTo(false);
            keyWordList = keyWordMapper.selectByExample(keyWordExample);
        }else{
            //该情况是修改keyword
            keyWordExample.createCriteria().andKeywordEqualTo(keyword.getKeyword()).andIdNotEqualTo(keyword.getId()).andDeletedEqualTo(false);
            keyWordList = keyWordMapper.selectByExample(keyWordExample);
        }
        if(keyWordList.size() == 0){
            return false;
        }
        return true;
    }
}

package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.KeyWord;
import com.cskaoyan.mall.bean.KeyWordBO;
import com.cskaoyan.mall.bean.KeyWordInsertVO;

public interface KeyWordService {
    BaseData queryKeyWord(KeyWordBO keyWordBO);

    KeyWordInsertVO createKeyWord(KeyWord keyWord);

    KeyWordInsertVO updateKeyWord(KeyWord keyWord);

    void deleteKeyWord(KeyWord keyWord);
}

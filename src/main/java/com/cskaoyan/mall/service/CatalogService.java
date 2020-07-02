package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.VO.wx.CatalogCurrentVO;
import com.cskaoyan.mall.bean.VO.wx.CatalogVO;

/**
 * @author 杨
 * @create 2020-07-02 11:41
 */
public interface CatalogService {
    CatalogVO catalogIndex();

    CatalogCurrentVO catalogCurrent(Integer id);
}

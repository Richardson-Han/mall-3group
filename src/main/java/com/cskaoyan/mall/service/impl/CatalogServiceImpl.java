package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.bean.CategoryExample;
import com.cskaoyan.mall.bean.VO.wx.CatalogCurrentVO;
import com.cskaoyan.mall.bean.VO.wx.CatalogVO;
import com.cskaoyan.mall.mapper.CategoryMapper;
import com.cskaoyan.mall.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 杨
 * @create 2020-07-02 11:41
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public CatalogVO catalogIndex() {
        //先获取主列表
        List<Category> categoryList = categoryMapper.selectCategoryList();

        //获取主列表的第一个元素
        Category currentCategory = categoryList.get(0);
        //获取首位元素的子列表
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andPidEqualTo(currentCategory.getId());
        List<Category> currentSubCategory = categoryMapper.selectByExample(categoryExample);

        CatalogVO catalogVO = new CatalogVO(categoryList,currentCategory,currentSubCategory);
        return catalogVO;
    }

    @Override
    public CatalogCurrentVO catalogCurrent(Integer id) {
        //获取主列表元素
        Category currentCategory = categoryMapper.selectByPrimaryKey(id);

        //获取子列表元素
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andPidEqualTo(currentCategory.getId());
        List<Category> currentSubCategory = categoryMapper.selectByExample(categoryExample);

        CatalogCurrentVO currentVO = new CatalogCurrentVO(currentCategory,currentSubCategory);

        return currentVO;
    }
}

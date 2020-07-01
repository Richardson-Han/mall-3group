package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.bean.CategoryVo;
import org.springframework.stereotype.Service;

import java.util.List;

/* *
@author  Walker-胡
@create 2020-06-28 20:46
*/

public interface CategoryService {

    List<CategoryVo> queryL1();

    List<Category> queryList();

    int addCategory(Category category);

    int updateCategory(Category category);

    boolean deleteCategory(Category category);

    List<Category> wxselectLimitTen();
}

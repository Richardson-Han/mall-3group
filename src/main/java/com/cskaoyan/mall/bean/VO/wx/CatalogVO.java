package com.cskaoyan.mall.bean.VO.wx;

import com.cskaoyan.mall.bean.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 杨
 * @create 2020-07-02 11:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogVO {

    List<Category> categoryList;

    Category currentCategory;

    List<Category> currentSubCategory;

}

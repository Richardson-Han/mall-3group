package com.cskaoyan.mall.bean.VO.wx;


import com.cskaoyan.mall.bean.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 杨
 * @create 2020-07-02 13:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogCurrentVO {

    Category currentCategory;

    List<Category> currentSubCategory;

}

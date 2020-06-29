package com.cskaoyan.mall.bean.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCatAndBrandVO<T>{
    List<T> brandList;
    List<T> categoryList;
}

package com.cskaoyan.mall.bean.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 杨
 * @create 2020-07-01 23:48
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WXBrandListData<T> {

    List<T> brandList;

    long totalPages;
}

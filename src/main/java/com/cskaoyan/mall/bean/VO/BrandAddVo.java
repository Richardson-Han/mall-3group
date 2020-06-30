package com.cskaoyan.mall.bean.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/* *
@author  Walker-胡
@create 2020-06-28 18:10
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandAddVo {
    private String name;
    private String desc;
    private BigDecimal floorPrice;
    private String picUrl;
}

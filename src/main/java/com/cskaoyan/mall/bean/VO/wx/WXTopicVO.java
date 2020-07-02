package com.cskaoyan.mall.bean.VO.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/* *
@author  Walker-胡
@create 2020-07-01 15:42
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WXTopicVO {
    private Integer id;

    private String title;

    private String subtitle;

    private BigDecimal price;

    private String readCount;

    private String picUrl;

}

package com.cskaoyan.mall.bean.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGoods {
    private String goodsName;

    private Integer id;

    private Integer number;

    private String picUrl;
}

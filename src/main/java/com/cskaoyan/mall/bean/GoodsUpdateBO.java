package com.cskaoyan.mall.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsUpdateBO {
    List<GoodsAttribute> attributes;
    Goods goods;
    List<GoodsProduct> products;
    List<GoodsSpec> specifications;
}

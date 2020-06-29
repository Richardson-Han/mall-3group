package com.cskaoyan.mall.bean.BO;

import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.GoodsAttribute;
import com.cskaoyan.mall.bean.GoodsProduct;
import com.cskaoyan.mall.bean.GoodsSpec;
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

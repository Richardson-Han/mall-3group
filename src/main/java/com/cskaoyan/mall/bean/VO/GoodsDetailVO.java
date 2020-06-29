package com.cskaoyan.mall.bean.VO;

import com.cskaoyan.mall.bean.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailVO<T> {
    List<T> attributes;
    List<T> categoryIds;
    Goods goods;
    List<T> products;
    List<T> specifications;
}

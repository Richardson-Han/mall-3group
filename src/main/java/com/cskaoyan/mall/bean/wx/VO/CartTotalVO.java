package com.cskaoyan.mall.bean.wx.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Fang
 * @create 2020/7/1-16:45
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartTotalVO {
    //被选中的商品总的价格， 单价×数量
    Double checkedGoodsAmount;
    //被选择的全部商品的数量
    Integer checkedGoodsCount;
    //商品总价，不需要被选中
    Double goodsAmount;
    //商品总数量，不需要被选中
    Integer goodsCount;
}

package com.cskaoyan.mall.bean.wx.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Fang
 * @create 2020/7/1-22:15
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateBO {
    Integer goodsId;
    Integer id;
    Integer number;
    Integer productId;
}

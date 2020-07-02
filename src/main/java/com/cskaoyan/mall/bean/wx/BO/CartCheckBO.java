package com.cskaoyan.mall.bean.wx.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Fang
 * @create 2020/7/1-20:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartCheckBO {
    List<Integer> productIds;
    Integer isChecked;
}

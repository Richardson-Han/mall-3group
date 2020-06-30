package com.cskaoyan.mall.bean.wx;

import com.cskaoyan.mall.bean.Goods;
import lombok.Data;

/**
 * @author 韩
 * @create 2020-06-30 3:58
 */
@Data
public class GroupBuy {
    Double groupon_price;
    Goods goods;
    Integer groupon_member;
}

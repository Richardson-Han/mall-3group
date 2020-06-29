package com.cskaoyan.mall.bean.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigOrderBO {
    String cskaoyan_mall_order_comment;
    String cskaoyan_mall_order_unconfirm;
    String cskaoyan_mall_order_unpaid;
}

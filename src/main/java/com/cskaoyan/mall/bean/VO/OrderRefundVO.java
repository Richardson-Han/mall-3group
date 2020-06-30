package com.cskaoyan.mall.bean.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/* *
@author  Walker-胡
@create 2020-06-29 16:14
*/

/*
* 订单退款
* */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRefundVO {
    private Integer orderId;
    private BigDecimal refundMoney;

}

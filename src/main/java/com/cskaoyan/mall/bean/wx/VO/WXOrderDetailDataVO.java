package com.cskaoyan.mall.bean.wx.VO;

import com.cskaoyan.mall.bean.OrderGoods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WXOrderDetailDataVO {
    List<OrderGoods> orderGoods;
    WXOrderInfoVO orderInfo;
}

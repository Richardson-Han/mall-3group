package com.cskaoyan.mall.bean.wx.VO;

import com.cskaoyan.mall.bean.wx.HandleOption;
import com.cskaoyan.mall.bean.wx.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WXOrderInfoVO {
    private BigDecimal actualPrice;
    private Date addTime;
    private String address;
    private String consignee;
    private BigDecimal couponPrice;
    private BigDecimal freightPrice;
    private BigDecimal goodsPrice;
    private HandleOption handleOption;
    private Integer id;
    private String mobile;
    private String orderSn;
    private String orderStatusText;

    public void setOrderStatusText(Short orderStatus) {
        String text = null;
        int Status = orderStatus.intValue();
        if(Status ==(OrderStatus.ORDER_UPPAID.getCode())){
            text = OrderStatus.ORDER_UPPAID.getValue();
        }
        if(Status== (OrderStatus.USER_CANCEllED.getCode())){
            text=OrderStatus.USER_CANCEllED.getValue();
        }
        if(Status== (OrderStatus.SYSTEM_CANcellED.getCode())){
            text=OrderStatus.SYSTEM_CANcellED.getValue();
        }
        if(Status== (OrderStatus.ORDER_PAID.getCode())){
            text=OrderStatus.ORDER_PAID.getValue();
        }
        if(Status== (OrderStatus.REQUEST_REFUND.getCode())){
            text=OrderStatus.REQUEST_REFUND.getValue();
        }
        if(Status== (OrderStatus.ORDER_REFUDED.getCode())){
            text=OrderStatus.ORDER_REFUDED.getValue();
        }
        if(Status== (OrderStatus.ORDER_DELIVERED.getCode())){
            text=OrderStatus.ORDER_DELIVERED.getValue();
        }
        if(Status== (OrderStatus.USER_RECEIPT.getCode())){
            text=OrderStatus.USER_RECEIPT.getValue();
        }
        if(Status== (OrderStatus.SYSTEM_RECEIPT.getCode())){
            text=OrderStatus.SYSTEM_RECEIPT.getValue();
        }
        this.orderStatusText = text;
    }
}

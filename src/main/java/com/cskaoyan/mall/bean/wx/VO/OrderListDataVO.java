package com.cskaoyan.mall.bean.wx.VO;

import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.wx.HandleOption;
import com.cskaoyan.mall.bean.wx.OrderGoods;
import com.cskaoyan.mall.bean.wx.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OrderListDataVO {
    private BigDecimal actualPrice;

    private List<OrderGoods> goodsList;

    private HandleOption handleOption;

    private Integer id;

    private Boolean isGroupin;

    private String orderSn;

    private String orderStatusText;

    public OrderListDataVO() {
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public List<OrderGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OrderGoods> goodsList) {
        this.goodsList = goodsList;
    }

    public HandleOption getHandleOption() {
        return handleOption;
    }

    public void setHandleOption(HandleOption handleOption) {
        this.handleOption = handleOption;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getGroupin() {
        return isGroupin;
    }

    public void setIsGroupin(Boolean groupin) {
        isGroupin = groupin;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getOrderStatusText() {
        return orderStatusText;
    }

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

package com.cskaoyan.mall.bean.wx.enumeration;

public enum OrderStatus {
    ORDER_UPPAID(101, "待付款"),
    USER_CANCEllED(102, "用户取消"),
    SYSTEM_CANcellED(103, "系统取消"),
    ORDER_PAID(201, "已付款"),
    REQUEST_REFUND(202, "申请退款"),
    ORDER_REFUDED(203, "已退款"),
    ORDER_DELIVERED(301, "已发货"),
    USER_RECEIPT(401, "已收货"),
    SYSTEM_RECEIPT(402, "系统收货");

    private Integer code;
    private String value;

    OrderStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

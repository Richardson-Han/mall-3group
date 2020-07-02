package com.cskaoyan.mall.bean.VO;

/* * 
@author  Walker-胡
@create 2020-06-29 16:39
*/
public class OrderStatusVO {

    /*
    * 101:未付款    102：用户取消    103：系统取消
    * 201：已付款   202：申请退款    203：已退款
    * 301：已发货   401：用户收货    402：系统收货
    * */
    public final static Short order_uppaid=101;
    public final static Short user_cancelled=102;
    public final static Short system_cancelled=103;
    public final static Short order_paid=201;
    public final static Short request_refund=202;
    public final static Short order_refuded=203;
    public final static Short order_delivered=301;
    public final static Short user_receipt=401;
    public final static Short system_receipt=402;
}

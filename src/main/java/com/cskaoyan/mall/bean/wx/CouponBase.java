package com.cskaoyan.mall.bean.wx;

import com.cskaoyan.mall.bean.Coupon;
import lombok.Data;

import java.util.List;

/**
 * @author 韩
 * @create 2020-06-30 23:36
 */
@Data
public class CouponBase {
    List<Coupon> data;
    Integer count;
}

package com.cskaoyan.mall.bean;

import com.cskaoyan.mall.bean.wx.CouponBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseData<T> {
    List<T> items;
    long total;
}

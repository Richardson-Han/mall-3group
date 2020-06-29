package com.cskaoyan.mall.bean.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于接收商场配置请求体和响应体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigMallBO {
    String cskaoyan_mall_mall_address;

    String cskaoyan_mall_mall_name;

    String cskaoyan_mall_mall_phone;

    String cskaoyan_mall_mall_qq;
}

package com.cskaoyan.mall.bean.VO.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨
 * @create 2020-07-01 16:38
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WXAddressDetailVO {
    Integer id;

    String name;

    String address;

    String mobile;

    Integer provinceId;

    String provinceName;

    Integer cityId;

    String cityName;

    Integer areaId;

    String areaName;

    Boolean isDefault;
}

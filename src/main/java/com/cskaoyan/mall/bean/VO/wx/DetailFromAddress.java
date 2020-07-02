package com.cskaoyan.mall.bean.VO.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨
 * @create 2020-07-01 17:01
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailFromAddress {
    Integer id;

    String name;

    String address;

    String mobile;

    Integer provinceId;

    Integer cityId;

    Integer areaId;

    Boolean isDefault;
}

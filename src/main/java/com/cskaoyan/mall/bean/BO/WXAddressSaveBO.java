package com.cskaoyan.mall.bean.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨
 * @create 2020-07-01 20:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WXAddressSaveBO {

    Integer id;

    String name;

    String address;

    Integer cityId;

    Integer provinceId;

    Integer areaId;

    Boolean isDefault;

    String mobile;
}

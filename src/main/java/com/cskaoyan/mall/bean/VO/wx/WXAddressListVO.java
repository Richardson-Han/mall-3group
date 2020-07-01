package com.cskaoyan.mall.bean.VO.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨
 * @create 2020-07-01 13:34
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WXAddressListVO {

    Integer id;

    String name;

    String detailedAddress;

    String mobile;

    boolean isDefault;

}

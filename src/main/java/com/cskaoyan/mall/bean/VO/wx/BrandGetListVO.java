package com.cskaoyan.mall.bean.VO.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 杨
 * @create 2020-07-01 23:34
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandGetListVO {

    Integer id;

    Double floorPrice;

    String name;

    String picUrl;

    String desc;
}

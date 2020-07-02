package com.cskaoyan.mall.bean.wx.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Fang
 * @create 2020/7/2-12:49
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomCollectVO {
    Integer id;
    Integer valueId;
    Byte type;
    String name;
    String picUrl;
    Double retailPrice;
    String brief;
}

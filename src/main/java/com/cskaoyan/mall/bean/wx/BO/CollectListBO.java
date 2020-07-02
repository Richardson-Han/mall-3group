package com.cskaoyan.mall.bean.wx.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Fang
 * @create 2020/7/2-12:40
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectListBO {
    Byte type;
    Integer page;
    Integer size;
}

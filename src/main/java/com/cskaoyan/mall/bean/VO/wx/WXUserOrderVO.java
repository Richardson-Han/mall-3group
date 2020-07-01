package com.cskaoyan.mall.bean.VO.wx;

import com.cskaoyan.mall.bean.wx.WXOrderState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 韩
 * @create 2020-06-30 22:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WXUserOrderVO {
    WXOrderState order;
}

package com.cskaoyan.mall.bean.wx;

import lombok.Data;

/**
 * @author 韩
 * @create 2020-06-30 20:34
 */
@Data
public class WXOrderState {
    Integer unrecv;

    Integer uncomment;

    Integer unpaid;

    Integer unship;
}

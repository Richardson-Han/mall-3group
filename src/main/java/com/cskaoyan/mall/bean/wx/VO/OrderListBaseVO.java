package com.cskaoyan.mall.bean.wx.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListBaseVO {
    private Integer count;

    private List<OrderListDataVO> data;

    private Integer totalPages;
}

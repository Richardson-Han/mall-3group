package com.cskaoyan.mall.bean.wx.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCommentBO {
    private String content;

    private Boolean hasPicture;

    private Integer orderGoodsId;

    private String[] picUrls;

    private Short star;
}

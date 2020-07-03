package com.cskaoyan.mall.bean.VO.wx;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WXGoodsVO {

    private String brief;

    private String picUrl;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT +8")
    private Date addTime;

    private Integer goodsId;

    private String name;

    private Integer id;

    private BigDecimal retailPrice;

}
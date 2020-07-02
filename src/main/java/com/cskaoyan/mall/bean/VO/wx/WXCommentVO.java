package com.cskaoyan.mall.bean.VO.wx;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.javafx.collections.MappingChange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

/* *
@author  Walker-胡
@create 2020-07-01 22:47
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WXCommentVO {
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss",timezone = "GMT+8")
    private Date addTime;

    private String content;
    private String[] picList;
    private WXUserInfoVO userInfo;
}

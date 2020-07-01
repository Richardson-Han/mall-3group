package com.cskaoyan.mall.bean.VO.wx;

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
    private Date addTime;
    private String content;
    private String[] picList;
    private WXUserInfoVO userInfo;
}

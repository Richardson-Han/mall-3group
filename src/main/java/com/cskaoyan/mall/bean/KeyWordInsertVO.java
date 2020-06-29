package com.cskaoyan.mall.bean;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyWordInsertVO {
    Integer id;
    String keyword;
    String url;
    Boolean isHot;
    Boolean isDefault;
    Date addTime;
    Date updateTime;

}

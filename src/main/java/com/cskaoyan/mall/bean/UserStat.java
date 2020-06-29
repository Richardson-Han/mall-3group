package com.cskaoyan.mall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStat {
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    Date day;
    int users;
}

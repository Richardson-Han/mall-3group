package com.cskaoyan.mall.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreateVO {

    Integer id;

    String username;

    String password;

    ArrayList<Integer> roleIds;

    Date updateTime;

    Date addTime;

    String avatar;
}

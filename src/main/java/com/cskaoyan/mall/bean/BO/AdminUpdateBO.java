package com.cskaoyan.mall.bean.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateBO {

    Integer id;

    String username;

    String password;

    ArrayList<Integer> roleIds;

    String avatar;
}

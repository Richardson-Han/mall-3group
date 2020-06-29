package com.cskaoyan.mall.bean.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminListVO {

    Integer id;

    ArrayList<Integer> roleIds;

    String username;

    String avatar;

}

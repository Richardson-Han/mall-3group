package com.cskaoyan.mall.bean.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/***
 * @author 社会主义好
 * @date 2020-06-30 3:55 星期二 
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoVO {

    String avatar;

    String name;

    List<String> perms;

    List<String> roles;
}

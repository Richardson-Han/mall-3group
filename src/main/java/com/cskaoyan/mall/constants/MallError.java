package com.cskaoyan.mall.constants;

/* * 
@author  Walker-胡
@create 2020-07-03 17:05
*/

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MallError extends RuntimeException {

    String errmsg;
    Integer errno;
}

package com.cskaoyan.mall.service;

import java.util.Map;

/* *
@author  Walker-胡
@create 2020-06-30 19:12
*/
public interface WXFootprintService {
 Map  queryWXFootprintList(Integer page, Integer size);

    boolean deleteWXFootprint(Integer id);
}

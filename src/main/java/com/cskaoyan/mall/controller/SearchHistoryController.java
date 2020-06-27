package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.BaseRespVo;
import com.cskaoyan.mall.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author 社会主义好
 * @date 2020-06-27 8:09 星期六 
 *
 */
@RequestMapping("admin/history")
@RestController
public class SearchHistoryController {
    @Autowired
    SearchHistoryService searchHistoryService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit, String sort, String order, Integer userId, String keyword){
        BaseData baseData = searchHistoryService.queryHistory(page, limit, sort, order, userId, keyword);
        return BaseRespVo.ok(baseData);
    }
}

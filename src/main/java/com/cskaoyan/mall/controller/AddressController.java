package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author 社会主义好
 * @date 2020-06-26 18:15 星期五 
 *
 */
@RestController
@RequestMapping("admin/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit, String sort, String order, String name, Integer userId) {

        BaseData baseData = addressService.queryAddress(page, limit, sort, order, name, userId);
        return BaseRespVo.ok(baseData);
    }
}

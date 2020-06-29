package com.cskaoyan.mall.controller;


import com.cskaoyan.mall.bean.Region;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/* *
@author  Walker-胡
@create 2020-06-28 10:52
*/
@RestController
@RequestMapping("admin")
//@CrossOrigin

public class RegionController {
    @Autowired
   private RegionService regionService;
    @RequestMapping(value = "region/list", method= RequestMethod.GET)
    public BaseRespVo getALLRegions(){
        List<Region> regionList=regionService.queryRegionList ();
        return BaseRespVo.ok (regionList);
    }


}

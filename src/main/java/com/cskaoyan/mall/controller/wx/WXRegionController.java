package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Region;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.log.LogInputStream;

import java.util.List;
import java.util.Map;

/* *
@author  Walker-胡
@create 2020-07-01 20:21
*/
@RestController
@RequestMapping("/wx/region")
public class WXRegionController {
    @Autowired
    RegionService regionService;
    @GetMapping("/list")
    public BaseRespVo getRegionList(@RequestParam("pid") Integer pid){
        List<Region> list = regionService.queryWXRegionList(pid);
        return BaseRespVo.ok (list);
    }
}

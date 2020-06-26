package com.cskaoyan.mall.controller.yang_AdminController;


import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.BaseRespVo;
import com.cskaoyan.mall.service.yang_AdminService.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/admin")
public class AdminController {

    @Autowired
    AdminService adminService;


    //获取所有管理员信息
    @RequestMapping("list")
    public BaseRespVo getAlladmins(Integer page,Integer limit,String sort,String order){
        BaseData baseData = adminService.getAdmins(page,limit,sort,order);
        return BaseRespVo.ok(baseData);
    }
}

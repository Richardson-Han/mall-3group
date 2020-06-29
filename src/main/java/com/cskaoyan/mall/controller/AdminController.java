package com.cskaoyan.mall.controller;


import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.BO.AdminCreateBO;
import com.cskaoyan.mall.bean.BO.AdminUpdateBO;
import com.cskaoyan.mall.bean.VO.AdminCreateVO;
import com.cskaoyan.mall.bean.VO.AdminUpdateVO;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    //获取管理员信息
    @RequestMapping("list")
    public BaseRespVo getAlladmins(Integer page, Integer limit, String username, String sort, String order){
        BaseData baseData = adminService.getAdmins(page,limit,username,sort,order);
        return BaseRespVo.ok(baseData);
    }

    //新建admin
    @PostMapping("create")
    public BaseRespVo createAdmin(@RequestBody AdminCreateBO adminCreateBO){
        AdminCreateVO admin = adminService.createAdmin(adminCreateBO);
        if ( admin != null ){
            return BaseRespVo.ok(admin);
        }else {
            return BaseRespVo.error();
        }
    }

    //更新管理员数据
    @PostMapping("update")
    public BaseRespVo updateAdmin(@RequestBody AdminUpdateBO adminUpdateBO){
        AdminUpdateVO adminUpdateVO = adminService.updateAdmin(adminUpdateBO);
        if (adminUpdateVO != null){
            return BaseRespVo.ok(adminUpdateVO);
        }
        return BaseRespVo.error();
    }

    //删除
    @PostMapping("delete")
    public BaseRespVo deleteAdmin(@RequestBody AdminUpdateBO adminUpdateBO){
        Integer result = adminService.deleteAdmin(adminUpdateBO);
        if ( result > 0 ){
            return BaseRespVo.ok();
        }
        return BaseRespVo.error();
    }
}

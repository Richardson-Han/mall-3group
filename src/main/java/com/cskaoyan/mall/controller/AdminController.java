package com.cskaoyan.mall.controller;


import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.BO.AdminCreateBO;
import com.cskaoyan.mall.bean.BO.AdminUpdateBO;
import com.cskaoyan.mall.bean.VO.AdminCreateVO;
import com.cskaoyan.mall.bean.VO.AdminUpdateVO;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.AdminService;
import com.cskaoyan.mall.service.LogService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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

    @Autowired
    LogService logService;

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
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipals().getPrimaryPrincipal();
        if ( admin != null ){
            logService.setAdminCreate(username);
            return BaseRespVo.ok(admin);
        }else {
            return BaseRespVo.error();
        }
    }

    //更新管理员数据
    @PostMapping("update")
    public BaseRespVo updateAdmin(@RequestBody AdminUpdateBO adminUpdateBO){
        AdminUpdateVO adminUpdateVO = adminService.updateAdmin(adminUpdateBO);
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipals().getPrimaryPrincipal();
        if (adminUpdateVO != null){
            logService.updateAdmin(username,adminUpdateBO.getUsername());
            return BaseRespVo.ok(adminUpdateVO);
        }
        return BaseRespVo.error();
    }

    //删除
    @PostMapping("delete")
    public BaseRespVo deleteAdmin(@RequestBody AdminUpdateBO adminUpdateBO){
        Integer result = adminService.deleteAdmin(adminUpdateBO);
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipals().getPrimaryPrincipal();
        if ( result > 0 ){
            logService.deleteAdmin(username);
            return BaseRespVo.ok();
        }
        return BaseRespVo.error();
    }
}

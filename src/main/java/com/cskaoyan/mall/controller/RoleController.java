package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    //获取roleOptions列表
    @RequestMapping("options")
    public BaseRespVo getAllOptions(){
        List<RoleOptionsVO> roles = roleService.getRoleList();
        return BaseRespVo.ok(roles);
    }

    //获取
    @RequestMapping("list")
    public BaseRespVo getRoleList(Integer page,Integer limit,String name,String sort,String order){
        BaseData baseData = roleService.getRoleByName(page,limit,name,sort,order);
        return BaseRespVo.ok(baseData);
    }

    @PostMapping("create")
    public BaseRespVo createRole(@RequestBody RoleCreateBO roleCreateBO){
        Role role = roleService.createRole(roleCreateBO);
        if (role != null){
            return BaseRespVo.ok(role);
        }
        return BaseRespVo.error();
    }

    @PostMapping("update")
    public BaseRespVo updateRole(@RequestBody Role role){
        Integer result = roleService.updateRole(role);
        if ( result > 0 ){
            return BaseRespVo.ok();
        }
        return BaseRespVo.error();
    }

    @PostMapping("delete")
    public BaseRespVo deleteRole(@RequestBody Role role){
        Integer result = roleService.deleteRole(role);
        if ( result > 0 ){
            return BaseRespVo.ok();
        }
        return BaseRespVo.error();
    }
}

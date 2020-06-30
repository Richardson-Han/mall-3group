package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.BO.RoleCreateBO;
import com.cskaoyan.mall.bean.BO.RolePermissionBO;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.VO.RoleOptionsVO;
import com.cskaoyan.mall.bean.VO.RolePermissionVO;
import com.cskaoyan.mall.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /*
    *
    * */
    @GetMapping("permissions")
    public BaseRespVo getRolePermissions(Integer roleId){
        RolePermissionVO rolePermissionVO = roleService.getRolePermissions(roleId);
        if (rolePermissionVO != null){
            return BaseRespVo.ok(rolePermissionVO);
        }
        return BaseRespVo.error();
    }

    @PostMapping("permissions")
    public BaseRespVo setRolePermissions(@RequestBody RolePermissionBO rolePermissionBO){
        Integer result = roleService.setRolePermissions(rolePermissionBO);
        if ( result > 0 ){
            return BaseRespVo.ok();
        }
        return BaseRespVo.error();
    }
}

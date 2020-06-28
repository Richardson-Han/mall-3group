package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.VO.RoleOptionsVO;
import com.cskaoyan.mall.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
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


}

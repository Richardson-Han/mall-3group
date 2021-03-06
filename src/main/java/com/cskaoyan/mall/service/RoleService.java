package com.cskaoyan.mall.service;


import com.cskaoyan.mall.bean.BO.RolePermissionBO;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Role;
import com.cskaoyan.mall.bean.BO.RoleCreateBO;
import com.cskaoyan.mall.bean.VO.RoleOptionsVO;
import com.cskaoyan.mall.bean.VO.RolePermissionVO;


import java.util.List;

public interface RoleService {
    List<RoleOptionsVO> getRoleList();

    BaseData getRoleByName(Integer page, Integer limit, String name, String sort, String order);

    Role createRole(RoleCreateBO roleCreateBO);

    Integer updateRole(Role role);

    Integer deleteRole(Role role);

    RolePermissionVO getRolePermissions(Integer roleId);

    Integer setRolePermissions(RolePermissionBO rolePermissionBO);
}

package com.cskaoyan.mall.service.impl;


import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.BO.RoleCreateBO;
import com.cskaoyan.mall.bean.BO.RolePermissionBO;
import com.cskaoyan.mall.bean.VO.MenuChildVO;
import com.cskaoyan.mall.bean.VO.PermissionMenuVO;
import com.cskaoyan.mall.bean.VO.RoleOptionsVO;
import com.cskaoyan.mall.bean.VO.RolePermissionVO;
import com.cskaoyan.mall.mapper.MenuMapper;
import com.cskaoyan.mall.mapper.PermissionMapper;
import com.cskaoyan.mall.mapper.RoleMapper;
import com.cskaoyan.mall.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.System;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    MenuMapper menuMapper;

    //options接口,只获取name和desc
    @Override
    public List<RoleOptionsVO> getRoleList() {
        List<RoleOptionsVO> roles = roleMapper.selectRoleOptions();
        return roles;
    }

    //根据传入的name获取相似的role
    @Override
    public BaseData getRoleByName(Integer page, Integer limit, String name, String sort, String order) {
        RoleExample adminExample = new RoleExample();
        adminExample.setOrderByClause(sort + " " +order);
        PageHelper.startPage(page,limit);
        if (name != null){
            adminExample.createCriteria().andNameLike("%" + name + "%");
        }
        List<Role> roles = roleMapper.selectByExample(adminExample);
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        long total = pageInfo.getTotal();
        return new BaseData(roles,total);
    }

    @Override
    public Role createRole(RoleCreateBO roleCreateBO) {
        Role role = new Role();
        role.setName(roleCreateBO.getName());
        role.setDesc(roleCreateBO.getDesc());
        role.setAddTime(new Date());
        role.setUpdateTime(new Date());
        roleMapper.insertSelective(role);
        role.setId(roleMapper.getLastId());
        return role;
    }

    @Override
    public Integer updateRole(Role role) {
        Integer result = roleMapper.updateByPrimaryKey(role);
        return result;
    }

    @Override
    public Integer deleteRole(Role role) {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andRoleIdEqualTo(role.getId());
        permissionMapper.deleteByExample(permissionExample);
        return roleMapper.deleteByPrimaryKey(role.getId());
    }

    @Override
    public RolePermissionVO getRolePermissions(Integer roleId) {
        //获取当前roleID的权限
        List<String> rolePerssions = permissionMapper.selectPermission(roleId);

        //获取所有权限
        MenuExample menuExample = new MenuExample();
        List<Menu> menus = menuMapper.selectByExample(menuExample);

        //对menu数据进行封装
        List<PermissionMenuVO> permissionMenuVOList = new ArrayList<>();
        for (int i = 0; i < menus.size() ; i++ ){
            Menu menu = menus.get(i);
            if (menu.getParentId() == 0){
                PermissionMenuVO permissionMenuVO = new PermissionMenuVO();
                permissionMenuVO.setId(menu.getName());
                permissionMenuVO.setLabel(menu.getLabel());
                List<Object> children = new ArrayList<>();
                for (int j = i + 1; j < menus.size(); j++ ){
                    Menu childMenu = menus.get( j );
                    if (childMenu.getParentId() == (i + 1)){
                        PermissionMenuVO childPermissionVO = new PermissionMenuVO();
                        childPermissionVO.setId(childMenu.getName());
                        childPermissionVO.setLabel(childMenu.getLabel());
                        List<Object> menuChildVOList = new ArrayList<>();
                        //第三级menuList封装
                        for ( int k = j + 1 ; k < menus.size() ; k++ ){
                            Menu endMenu = menus.get(k);
                            if (endMenu.getParentId() == (j + 1)){
                                MenuChildVO menuChildVO = new MenuChildVO();
                                menuChildVO.setApi(endMenu.getApi());
                                menuChildVO.setId(endMenu.getName());
                                menuChildVO.setLabel(endMenu.getLabel());
                                menuChildVOList.add(menuChildVO);
                            }
                        }
                        childPermissionVO.setChildren(menuChildVOList);
                        children.add(childPermissionVO);
                    }
                }
                permissionMenuVO.setChildren(children);
                permissionMenuVOList.add(permissionMenuVO);
            }
        }
        //对输出结果封装
        RolePermissionVO rolePermissionVO = new RolePermissionVO();
        rolePermissionVO.setSystemPermissions(permissionMenuVOList);
        rolePermissionVO.setAssignedPermissions(rolePerssions);
        return rolePermissionVO;
    }

    @Override
    public Integer setRolePermissions(RolePermissionBO rolePermissionBO) {
        List<String> permissions = rolePermissionBO.getPermissions();
        Integer result = 0;
        for (String permissionData : permissions){
            Permission permission = new Permission();
            permission.setAddTime(new Date());
            permission.setUpdateTime(new Date());
            permission.setPermission(permissionData);
            permission.setRoleId(rolePermissionBO.getRoleId());
            permissionMapper.insertSelective(permission);
            result++;
        }
        return result;
    }
}

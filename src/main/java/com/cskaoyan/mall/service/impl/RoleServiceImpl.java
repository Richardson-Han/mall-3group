package com.cskaoyan.mall.service.impl;


import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.BO.RoleCreateBO;
import com.cskaoyan.mall.bean.VO.RoleOptionsVO;
import com.cskaoyan.mall.mapper.RoleMapper;
import com.cskaoyan.mall.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

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
        return roleMapper.deleteByPrimaryKey(role.getId());
    }
}

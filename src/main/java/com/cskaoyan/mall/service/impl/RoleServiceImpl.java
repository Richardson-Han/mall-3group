package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Role;
import com.cskaoyan.mall.bean.RoleExample;
import com.cskaoyan.mall.bean.RoleOptionsVO;
import com.cskaoyan.mall.mapper.RoleMapper;
import com.cskaoyan.mall.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;


    @Override
    public List<RoleOptionsVO> getRoleList() {
        List<RoleOptionsVO> roles = roleMapper.selectRoleOptions();
        return roles;
    }
}

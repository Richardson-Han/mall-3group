package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.VO.RoleOptionsVO;
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

package com.cskaoyan.mall.service.yang_AdminService;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.yang_AdminModel.Admin;
import com.cskaoyan.mall.bean.yang_AdminModel.AdminExample;
import com.cskaoyan.mall.mapper.yang_Admin.AdminMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public BaseData getAdmins(Integer page, Integer limit, String sort, String order) {
        AdminExample adminExample = new AdminExample();
        adminExample.setOrderByClause(sort + " " +order);
        PageHelper.startPage(page,limit);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        PageInfo<Admin> pageInfo = new PageInfo<>(admins);
        long total = pageInfo.getTotal();
        return new BaseData(admins,total);
    }
}

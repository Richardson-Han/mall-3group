package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.AdminExample;
import com.cskaoyan.mall.bean.AdminListBO;
import com.cskaoyan.mall.bean.AdminListVO;
import com.cskaoyan.mall.mapper.AdminMapper;
import com.cskaoyan.mall.service.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    //获取管理员列表
    @Override
    public BaseData getAdmins(Integer page, Integer limit, String sort, String order) {
        AdminExample adminExample = new AdminExample();
        adminExample.setOrderByClause(sort + " " +order);
        PageHelper.startPage(page,limit);
        List<AdminListBO> admins = adminMapper.selectAllAdminsByExample(adminExample);
        List<AdminListVO> adminListVOList = getAdminsVO(admins);
        PageInfo<AdminListVO> pageInfo = new PageInfo<>(adminListVOList);
        long total = pageInfo.getTotal();
        return new BaseData(adminListVOList,total);
    }

    //将admin中的role_ids字符串数据转化为数组类型
    private List<AdminListVO> getAdminsVO(List<AdminListBO> admins) {
        List<AdminListVO> adminListVO = new ArrayList<>();
        for (AdminListBO admin : admins ) {
            ArrayList<Integer> roles = new ArrayList<>();
            String roleIds = admin.getRoleIds();
            roleIds = roleIds.replace("[","");
            roleIds = roleIds.replace("]","");
            roleIds = roleIds.replace(",","");
            for (int i = 0; i < roleIds.length(); i++){
                String num = roleIds.substring(i,i+1);
                roles.add(Integer.parseInt(num));
            }
            AdminListVO adminVO = new AdminListVO(admin.getId(),roles,admin.getUsername(),admin.getAvatar());
            adminListVO.add(adminVO);
        }
        return adminListVO;
    }
}

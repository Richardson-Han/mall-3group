package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.AdminMapper;
import com.cskaoyan.mall.service.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    //获取管理员列表
    @Override
    public BaseData getAdmins(Integer page, Integer limit,String username, String sort, String order) {
        AdminExample adminExample = new AdminExample();
        adminExample.setOrderByClause(sort + " " +order);
        PageHelper.startPage(page,limit);
        if (username != null){
            adminExample.createCriteria().andUsernameLike("%" + username + "%");
        }
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        List<AdminListVO> adminListVOList = getAdminsVO(admins);
        PageInfo<AdminListVO> pageInfo = new PageInfo<>(adminListVOList);
        long total = pageInfo.getTotal();
        return new BaseData(adminListVOList,total);
    }

    //新建管理员
    @Override
    public AdminCreateVO createAdmin(AdminCreateBO adminCreateBO) {
        String roleIds = adminCreateBO.getRoleIds().toString();
        Date addTinme = new Date();
        Date updateTime = new Date();
        Admin admin = new Admin();
        admin.setUsername(adminCreateBO.getUsername());
        admin.setAvatar(adminCreateBO.getAvatar());
        admin.setPassword(adminCreateBO.getPassword());
        admin.setAddTime(addTinme);
        admin.setUpdateTime(updateTime);
        admin.setRoleIds(roleIds);

        Integer result = adminMapper.insert(admin);

        if(result > 0){
            AdminCreateVO adminCreateVO = new AdminCreateVO();
            adminCreateVO.setId(adminMapper.getLastInsertId());
            adminCreateVO.setUsername(adminCreateBO.getUsername());
            adminCreateVO.setAddTime(addTinme);
            adminCreateVO.setPassword(adminCreateBO.getPassword());
            adminCreateVO.setAvatar(adminCreateBO.getAvatar());
            adminCreateVO.setRoleIds(adminCreateBO.getRoleIds());
            adminCreateVO.setUpdateTime(updateTime);
            return adminCreateVO;
        }
        return null;
    }

    @Override
    public AdminUpdateVO updateAdmin(AdminUpdateBO adminUpdateBO) {

        Date updateTime = new Date();
        //封装传入的admin数据
        Admin admin = new Admin();
        admin.setUpdateTime(updateTime);
        admin.setPassword(adminUpdateBO.getPassword());
        admin.setAvatar(adminUpdateBO.getAvatar());
        admin.setUsername(adminUpdateBO.getUsername());
        admin.setRoleIds(adminUpdateBO.getRoleIds().toString());

        //更新数据库
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andIdEqualTo(adminUpdateBO.getId());
        Integer result = adminMapper.updateByExampleSelective(admin,adminExample);

        //封装导出数据
        AdminUpdateVO adminUpdateVO = new AdminUpdateVO();
        adminUpdateVO.setId(adminUpdateBO.getId());
        adminUpdateVO.setAvatar(adminUpdateBO.getAvatar());
        adminUpdateVO.setPassword(adminUpdateBO.getPassword());
        adminUpdateVO.setUpdateTime(updateTime);
        adminUpdateVO.setUsername(adminUpdateBO.getUsername());
        return adminUpdateVO;
    }

    //删除
    @Override
    public Integer deleteAdmin(AdminUpdateBO adminUpdateBO) {
        return adminMapper.deleteByPrimaryKey(adminUpdateBO.getId());
    }

    //将admin中的role_ids字符串数据转化为数组类型
    private List<AdminListVO> getAdminsVO(List<Admin> admins) {
        List<AdminListVO> adminListVO = new ArrayList<>();
        for (Admin admin : admins ) {
            ArrayList<Integer> roles = new ArrayList<>();
            String roleIds = admin.getRoleIds();
            roleIds = roleIds.replace("[","");
            roleIds = roleIds.replace("]","");
            roleIds = roleIds.replace(",","");
            roleIds = roleIds.replace(" ","");
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

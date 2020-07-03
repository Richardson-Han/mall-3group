package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.BO.AdminCreateBO;
import com.cskaoyan.mall.bean.BO.AdminUpdateBO;
import com.cskaoyan.mall.bean.VO.AdminCreateVO;
import com.cskaoyan.mall.bean.VO.AdminUpdateVO;
import com.cskaoyan.mall.bean.VO.InfoVO;

import java.util.List;

public interface AdminService {
    BaseData getAdmins(Integer page, Integer limit,String username, String sort, String order);

    AdminCreateVO createAdmin(AdminCreateBO adminCreateBO);

    AdminUpdateVO updateAdmin(AdminUpdateBO adminUpdateBO);

    Integer deleteAdmin(AdminUpdateBO adminUpdateBO);

    InfoVO info(String username);

    String[] selectAllRoleid();

    String[] selectPermissionByRoleid(String roleId);

    String selectRoleidByUsername(String username);

    List<String> selectPasswordByName(String username);

    Integer selectLastId();
}

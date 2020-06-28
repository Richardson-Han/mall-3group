package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;

public interface AdminService {
    BaseData getAdmins(Integer page, Integer limit,String username, String sort, String order);

    AdminCreateVO createAdmin(AdminCreateBO adminCreateBO);

    AdminUpdateVO updateAdmin(AdminUpdateBO adminUpdateBO);

    Integer deleteAdmin(AdminUpdateBO adminUpdateBO);
}

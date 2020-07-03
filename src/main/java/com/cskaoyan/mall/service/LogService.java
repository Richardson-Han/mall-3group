package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.VO.AdminUpdateVO;

public interface LogService {
    BaseData getLogList(Integer page, Integer limit, String name, String sort, String order);

    void setLogin(String username);

    void setLogout(String username);

    void setAdminCreate(String username);

    void updateAdmin(String username,String updateName);

    void deleteAdmin(String username);
}

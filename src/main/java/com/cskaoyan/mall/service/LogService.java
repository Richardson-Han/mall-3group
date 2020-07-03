package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;

public interface LogService {
    BaseData getLogList(Integer page, Integer limit, String name, String sort, String order);

    void setLogin(String username);

    void setLogout(String username);

    void setAdminCreate(String username,String operationName, Integer id);

    void updateAdmin(String username,String updateName,String operationName);

    void deleteAdmin(String username,Integer id);

    void setUpdate(String username, String name, String operationName);
}

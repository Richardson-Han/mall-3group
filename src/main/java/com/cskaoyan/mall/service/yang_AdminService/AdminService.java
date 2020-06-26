package com.cskaoyan.mall.service.yang_AdminService;

import com.cskaoyan.mall.bean.BaseData;

public interface AdminService {
    BaseData getAdmins(Integer page, Integer limit, String sort, String order);
}

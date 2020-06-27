package com.cskaoyan.mall.service.szyService;

import com.cskaoyan.mall.bean.BaseData;

public interface UserService {

    BaseData queryUsers(Integer page, Integer limit, String sort, String order, String username, String mobile);

    Long getUserTotal();
}

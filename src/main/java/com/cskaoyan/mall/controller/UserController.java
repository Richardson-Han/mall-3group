package com.cskaoyan.mall.controller.szyController;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.BaseRespVo;
import com.cskaoyan.mall.service.szyService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit, String sort, String order, String username, String mobile){
        BaseData baseData = userService.queryUsers(page, limit, sort, order, username, mobile);
        return BaseRespVo.ok(baseData);
    }


}

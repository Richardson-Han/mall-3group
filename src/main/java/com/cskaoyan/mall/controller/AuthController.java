package com.cskaoyan.mall.controller.szyController;

import com.cskaoyan.mall.bean.BaseRespVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/auth")
public class AuthController {

    /**
     * 整合shiro的时候自己增加业务逻辑
     * @param map
     * @return
     */
    @RequestMapping("login")
    public BaseRespVo login(@RequestBody Map map){
        return BaseRespVo.ok("e2e629f2-b10c-4954-936a-5fb48349edd7");
    }

    /**
     * 整合shiro的时候在增加业务
     * @param token
     * @return
     */
    @RequestMapping("info")
    public BaseRespVo info(String token){
        Map data = new HashMap<String,Object>();
        data.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        data.put("name","admin123");
        List perms = new ArrayList();
        perms.add("*");
        List roles = new ArrayList();
        roles.add("超级管理员");
        data.put("perms", perms);
        data.put("roles", roles);
        return BaseRespVo.ok(data);
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public BaseRespVo logout(){
        return BaseRespVo.ok();
    }
}

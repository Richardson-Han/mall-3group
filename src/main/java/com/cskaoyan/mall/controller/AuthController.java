package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.shiro.MallToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/auth")
public class AuthController {

    /**
     * 整合shiro
     * 数据库原加密方式未知，为了登陆，
     * 请进入test中HanShiroTest类运行
     * HanMD5PasswordTest 方法
     * 将控制台输出的密码复制黏贴指数据库中对应值的位置
     * (只会生成32位的 不懂怎么弄成60位的相类似的)
     */
    @RequestMapping("login")
    public BaseRespVo login(@RequestBody Map map) {
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String passwordDB = new Md5Hash(password, username + "3group", 8).toString();
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new MallToken(username,passwordDB,"admin"));
            //微信端传入
            //subject.login(new MallToken(username,passwordDB,"wx"));
        } catch (Exception e) {
            return BaseRespVo.error("用户名或密码错误", 401);
        }
        Serializable id  = subject.getSession().getId();
        return BaseRespVo.ok(id);
    }

    /**
     * shiro未整合
     */

    @RequestMapping("info")
    public BaseRespVo info(String token) {
        Map data = new HashMap<String, Object>();
        data.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        data.put("name", "admin123");
        List perms = new ArrayList();
        perms.add("*");
        List roles = new ArrayList();
        roles.add("超级管理员");
        data.put("perms", perms);
        data.put("roles", roles);
        return BaseRespVo.ok(data);
    }

    /**
     * 退出已完成
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public BaseRespVo logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return BaseRespVo.ok();
    }

    @RequestMapping(value = "401")
    public BaseRespVo four01(){
        return BaseRespVo.ok();
    }
}

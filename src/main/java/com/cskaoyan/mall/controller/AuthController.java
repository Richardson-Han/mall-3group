package com.cskaoyan.mall.controller;


import com.cskaoyan.mall.bean.VO.InfoVO;
import com.cskaoyan.mall.service.AdminService;
import com.cskaoyan.mall.shiro.MallToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("admin/auth")
public class AuthController {

    @Autowired
    AdminService adminService;

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
            Serializable id  = subject.getSession().getId();
            return BaseRespVo.ok(id);
            //微信端传入
            //subject.login(new MallToken(username,passwordDB,"wx"));
        } catch (Exception e) {
            return BaseRespVo.error("用户名或密码错误", 401);
        }
    }

    /**
     * shiro已整合：登录不同权限的账号，会显示不同的页面
     * 登录 admin123 超级管理员权限
     * 登录 promotion123 推广管理员权限
     * 登录 mall123 商场管理员权限
     */

    @RequestMapping("info")
    public BaseRespVo info() {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        InfoVO infoVO = adminService.info(username);
        return BaseRespVo.ok(infoVO);
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

    private ArrayList<Integer> parseArrayList(String s) {
        s = s.replace("[", "").replace("]", "");
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ',') {
                integers.add(Integer.parseInt(s.substring(0, i)));
                s = s.substring(i+1);
                i = 0;//重新从0开始循环
            }
        }
        integers.add(Integer.parseInt(s));
        return integers;
    }
}

package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.VO.wx.WXUserInfoVO;
import com.cskaoyan.mall.bean.VO.wx.WXUserLoginVO;
import com.cskaoyan.mall.service.UserService;
import com.cskaoyan.mall.shiro.MallToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @author 韩
 * @create 2020-06-29 22:51
 */
@RestController
@RequestMapping("wx/auth")
public class WXAuthController {

    @Autowired
    UserService userService;

    /**
     * 整合shiro
     * 数据库原加密方式未知，为了登陆，
     * 请进入test中WXHanShiroTest类运行
     * HanMD5PasswordTest 方法
     */
    @RequiresGuest
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseRespVo login(@RequestBody Map map) {
        String password = (String) map.get("password");
        String username = (String) map.get("username");
        String passwordDB = new Md5Hash(password, username + "3groupWX", 3).toString();
        MallToken wxtoken = new MallToken(username, passwordDB, "wx");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(wxtoken);
            String token = (String) subject.getSession().getId();
            Date tokenExpire = new Date();
            WXUserInfoVO userInfoVO = userService.getUserInfo(username);
            WXUserLoginVO loginVO = new WXUserLoginVO(token, tokenExpire, userInfoVO);
            return BaseRespVo.ok(loginVO);
        } catch (Exception e) {
            System.out.println("*****************************挂了*****************************");
            return BaseRespVo.error("用户名或密码错误", 401);
        }
    }

    /**
     * 退出已完成
     */
    @RequiresAuthentication
    @RequiresUser
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public BaseRespVo logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return BaseRespVo.ok();
    }
}

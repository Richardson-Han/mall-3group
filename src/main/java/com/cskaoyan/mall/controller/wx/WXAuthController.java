package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.VO.wx.WXUserInfoVO;
import com.cskaoyan.mall.bean.VO.wx.WXUserLoginVO;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.UserService;
import com.cskaoyan.mall.shiro.MallToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
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
        MallToken wxtoken = new MallToken(username,passwordDB,"wx");
        Subject subject = SecurityUtils.getSubject();
        // try {
        //     subject.login(new MallToken(username, passwordDB, "wx"));
//         } catch (Exception e) {
//             System.out.println("挂了");
//             return BaseRespVo.error("用户名或密码错误", 401);
//         }finally {
        //     Serializable id = subject.getSession().getId();
        //     return BaseRespVo.ok(id);
        // }
        //韩
        //先登陆 后期补
//        Dataa data = new Dataa();
//        data.setToken(username);
//        data.setUserInfo();
//        data.setTokenExpire(new Date());
        //杨
        try {
            subject.login(wxtoken);
            String token = (String) subject.getSession().getId();
            Date tokenExpire = new Date();
            WXUserInfoVO userInfoVO = userService.getUserInfo(username);
            WXUserLoginVO loginVO = new WXUserLoginVO(token,tokenExpire,userInfoVO);
            return BaseRespVo.ok(loginVO);
        }catch (Exception e) {
            System.out.println("挂了");
            return BaseRespVo.error("用户名或密码错误", 401);
        }
    }

    /**
     * 退出已完成
     */
    @RequiresUser
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public BaseRespVo logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return BaseRespVo.ok();
    }

    class Dataa {
        UserInfo userInfo;
        Date tokenExpire;
        String token;

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo() {
            this.userInfo = new UserInfo();
        }

        public Date getTokenExpire() {
            return tokenExpire;
        }

        public void setTokenExpire(Date tokenExpire) {
            this.tokenExpire = tokenExpire;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = "j65dcjj0if0tf223567uwgu9a7t7b1z8";
        }

        class UserInfo {
            String nickname;
            String avatarUrl;

            public UserInfo() {
                super();
                setNickname("?");
                setAvatarUrl("!");
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = "test1";
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80";
            }
        }
    }
}

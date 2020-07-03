package com.cskaoyan.mall.utils;

import lombok.experimental.UtilityClass;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author 韩
 * @create 2020-07-01 12:55
 */
@UtilityClass
public class WXTokenUtils {

    public String requestToUsername(HttpServletRequest request) {
        String requestHeaderToken = request.getHeader("X-cskaoyan-mall-Admin-Token");
        if (requestHeaderToken == null){
            throw new RuntimeException("取不到对应的token值,请重新登陆账号");
        }
        Subject subject = SecurityUtils.getSubject();
        Serializable token = subject.getSession().getId();
        String username;
        if (token.equals(requestHeaderToken)) {
            username = (String) subject.getPrincipals().getPrimaryPrincipal();
            if (username != null) {
                return username;
            }
        }
        return "this token is error";
    }
}

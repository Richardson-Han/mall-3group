package com.cskaoyan.mall.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * @author 韩
 * @create 2020-07-01 12:55
 */
public class WXTokenUtils {
    public static String requestToUsername(HttpServletRequest request){
        String requestHeaderToken = request.getHeader("X-cskaoyan-mall-Admin-Token");
        Subject subject = SecurityUtils.getSubject();
        Serializable token = subject.getSession().getId();
        if (token.equals(requestHeaderToken)){
            String username = (String) subject.getPrincipals().getPrimaryPrincipal();
            return username;
        }
        return "this token is error";
    }
}

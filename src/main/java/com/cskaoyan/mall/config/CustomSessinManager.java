package com.cskaoyan.mall.config;



import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author 韩
 * @create 2020-06-29 1:26
 */
public class CustomSessinManager extends DefaultWebSessionManager {

    @Override
    protected Serializable getSessionId(ServletRequest srerequest, ServletResponse response) {
        HttpServletRequest request = (HttpServletRequest) srerequest;
        String sessionId = request.getHeader("X-cskaoyan-mall-Admin-Token");
        if (sessionId != null && !"".equals(sessionId)){
            return sessionId;
        }
        return super.getSessionId(srerequest, response);
    }
}

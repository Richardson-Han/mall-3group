package com.cskaoyan.mall.config;

import com.cskaoyan.mall.service.AdminService;
import com.cskaoyan.mall.shiro.AdminRealm;
import com.cskaoyan.mall.mapper.AdminMapper;
import com.cskaoyan.mall.shiro.WxRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.*;

/**
 * @author 韩
 * @create 2020-06-28 20:42
 */
@Configuration
public class ShiroConfig {

    @Autowired
    AdminService adminService;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //认证失败后重定向的url
        shiroFilterFactoryBean.setLoginUrl("/admin/auth/401");
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //key→请求url value→过滤器
        LinkedHashMap<String, String> fiterChainDefinitionMap = new LinkedHashMap<>();
        //发现自己本应通过的请求未通过缺失 自行增加("url","anon")
        //请用两个小账户测试
        fiterChainDefinitionMap.put("/admin/auth/login", "anon");
        fiterChainDefinitionMap.put("/admin/auth/401", "anon");
        fiterChainDefinitionMap.put("/admin/storage/create", "anon");
        fiterChainDefinitionMap.put("/admin/auth/info", "anon");

        fiterChainDefinitionMap.put("/wx/catalog/**","anon");
        fiterChainDefinitionMap.put("/wx/home/index","anon");
        fiterChainDefinitionMap.put("/wx/brand/list","anon");
        fiterChainDefinitionMap.put("/wx/brand/detail","anon");
        fiterChainDefinitionMap.put("/wx/coupon/list","anon");
        fiterChainDefinitionMap.put("/wx/coupon/list","anon");

        // fiterChainDefinitionMap.put("/**","perms[*]");*不需要设置 自动全权限

        //("admin/category/read","perms["perms[admin:category:read]"]")
        fiterChainDefinitionMap.put("admin/category/read", "perms[admin:category:read]");
        //取全部的roleid出来 做对应
        String[] Roleids = adminService.selectAllRoleid();

        for (String roleid : Roleids) {
            // ==1的 已经做了 跳过
            if (roleid.equals("1")) {
                continue;
            }
            //取url和权限值 的键值对
            String[] permissionByRoleids = adminService.selectPermissionByRoleid(roleid);
            if (permissionByRoleids == null) {
                System.out.println("roleid = " + roleid + ",this roleid not corresponding permission");
                continue;
            }
            String perms;
            for (String turePermission : permissionByRoleids) {
                perms = "perms[" + turePermission + "]";
                turePermission = turePermission.replace(":", "/");
                fiterChainDefinitionMap.put(turePermission, perms);
            }
        }
        fiterChainDefinitionMap.put("/admin/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(fiterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(AdminRealm adminRealm,
                                                     CustomAuhthenticator auhthenticator) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(adminRealm);
        defaultWebSecurityManager.setSessionManager(webSecurityManager());
        defaultWebSecurityManager.setAuthenticator(auhthenticator);
        return defaultWebSecurityManager;
    }

    /**
     * 声明式鉴权 注解需要的组件
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 使用映射处理异常: key为一场全类名 value为异常处理的请求
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.put("org.apache.shiro.authz.AuthorizationException", "");
        simpleMappingExceptionResolver.setExceptionMappings(mappings);
        return simpleMappingExceptionResolver;
    }

    @Bean
    public DefaultWebSessionManager webSecurityManager() {
        return new CustomSessionManager();
    }

    /**
     * 注册自定义的认证器
     */
    @Bean
    public CustomAuhthenticator auhthenticator(AdminRealm adminRealm, WxRealm wxRealm) {
        CustomAuhthenticator customAuhthenticator = new CustomAuhthenticator();
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(adminRealm);
        realms.add(wxRealm);
        customAuhthenticator.setRealms(realms);
        return customAuhthenticator;
    }
}

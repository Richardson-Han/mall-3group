package com.cskaoyan.mall.config;

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
    AdminMapper adminMapper;

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
        //微信权限设置，要是哪个网页不需要权限就能访问就在这添加fiterChainDefinitionMap.put("***", "anon");

        fiterChainDefinitionMap.put("/wx/home/**", "anon");
        fiterChainDefinitionMap.put("/wx/goods/**", "anon");
        fiterChainDefinitionMap.put("/wx/catalog/**", "anon");
        fiterChainDefinitionMap.put("/wx/auth/login", "anon");
        fiterChainDefinitionMap.put("/wx/search/**", "anon");
        fiterChainDefinitionMap.put("/wx/storage/**","anon");
        fiterChainDefinitionMap.put("/wx/**", "authc");

        //开发时先给全部权限
        fiterChainDefinitionMap.put("/wx/address/list", "authc");

        // fiterChainDefinitionMap.put("/**","perms[*]");*不需要设置 自动全权限

        //("admin/category/read","perms["perms[admin:category:read]"]")
        fiterChainDefinitionMap.put("admin/category/read", "perms[admin:category:read]");
        //取全部的roleid出来 做对应
        String[] Roleids = adminMapper.selectAllRoleid();

        for (String roleid : Roleids) {
            // ==1的 已经做了 跳过
            if (roleid.equals("1")) {
                continue;
            }
            //取url和权限值 的键值对
            LinkedHashMap<String, String> pathAndPerms = authorizationGroup(roleid);
            if (pathAndPerms == null) {
                System.out.println("roleid = " + roleid + ",this roleid not corresponding permissio");
                continue;
            }
            Iterator<Map.Entry<String, String>> iterator = pathAndPerms.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                //放键值对进去
                fiterChainDefinitionMap.put(next.getKey(), next.getValue());
            }
        }
        fiterChainDefinitionMap.put("/admin/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(fiterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    public LinkedHashMap<String, String> authorizationGroup(String RoleId) {
        String[] permissionByRoleids = adminMapper.selectPermissionByRoleid(RoleId);
        if (permissionByRoleids != null) {
            LinkedHashMap<String, String> stringStringLinkedHashMap = new LinkedHashMap<>();
            String perms;
            for (String turePermission : permissionByRoleids) {
                perms = "perms[" + turePermission + "]";
                turePermission = turePermission.replace(":", "/");
                stringStringLinkedHashMap.put(turePermission, perms);
            }
            return stringStringLinkedHashMap;
        } else {
            return null;
        }
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
        CustomSessionManager customSessionManager = new CustomSessionManager();
        return customSessionManager;
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

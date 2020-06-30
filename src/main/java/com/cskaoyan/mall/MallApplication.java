package com.cskaoyan.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.cskaoyan.mall.mapper")
@SpringBootApplication
public class MallApplication {
    /**
     * * 1.RequiresAuthentication:  该注解标注的类，实例，方法在访问或调用时，
     * * 当前Subject必须在当前session中已经过认证
     * * 2.RequiresGuest: 用于来宾任意访问
     * * 3.RequiresPermissions:  当前Subject需要拥有某些特定的权限时，才能执行被该注解标注的方法。
     * * 如果当前Subject不具有这样的权限，则方法不会被执行
     * * 4.RequiresRoles:  当前Subject必须拥有所有指定的角色时，才能访问被该注解标注的方法。
     * * 如果当前Subject不同时拥有所有指定角色，则方法不会执行还会抛出AuthorizationException异常。
     * * 5.RequiresUser:  当前Subject必须是应用的用户，才能访问或调用被该注解标注的类，实例，方法。
     */
    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }
}

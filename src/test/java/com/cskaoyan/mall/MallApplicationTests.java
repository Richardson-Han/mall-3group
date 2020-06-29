package com.cskaoyan.mall;

import com.cskaoyan.mall.mapper.AdminMapper;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class MallApplicationTests {

    @Autowired
    AdminMapper adminMapper;

    @Test
    void contextLoads() {
    }

    /**
     * 测试获取系统时间
     */
    @Test
    public void test1() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String expireTime = simpleDateFormat.format(new Date());
        //System.out.println(expireTime);
        System.out.println(new Date());
    }

    /**
     * admin表 role_id 获取 权限列表
     */
    @Test
    public void hantest() {
        System.out.println("******************************admin123******************************");
        String string1 = adminMapper.selectRoleidByUsername("admin123");
        System.out.println(string1);
        //对取出的数据做处理
        string1 = string1.replace("[", "");
        string1 = string1.replace("]", "");
        String[] strings = string1.split(",");
        List<String> permission = adminMapper.selectPermissionByRoleid(strings[0]);
        for (String s : permission) {
            System.out.println(s);
        }
        for (int i = 1; i < strings.length; i++) {
            permission.addAll(adminMapper.selectPermissionByRoleid(strings[i]));
            System.out.println("~~***********admin123***********~~");
            for (String s : permission) {
                System.out.println(s);
            }
        }

        //去重
        List<String> list = permission.stream().distinct().collect(Collectors.toList());

        System.out.println("去重后");
        for (String s : list) {
            System.out.println(s);
        }


        System.out.println("******************************promotion123******************************");

        String string2 = adminMapper.selectRoleidByUsername("promotion123");
        System.out.println(string2);
        //对取出的数据处理
        string2 = string2.replace("[", "");
        string2 = string2.replace("]", "");
        String[] strings2 = string2.split(",");
        List<String> permission2 = adminMapper.selectPermissionByRoleid(strings2[0]);
        for (String s : permission2) {
            System.out.println(s);
        }
        System.out.println("**~~~~~~~~~promotion123~~~~~~~~~~~~~**");
        for (int i = 1; i < strings2.length; i++) {
            permission.addAll(adminMapper.selectPermissionByRoleid(strings2[i]));
            System.out.println("**~~~~~~~~~promotion123~~~~~~~~~~~~~**");
            for (String s : permission) {
                System.out.println(s);
            }
        }

        System.out.println("******************************mall123******************************");

        String string3 = adminMapper.selectRoleidByUsername("mall123");
        System.out.println(string3);
    }

    /**
     * 权限访问测试 查看用户权限是否取对
     */
    @Test
    public void hantest2() {
        String string = adminMapper.selectRoleidByUsername("admin123");
        //取出的数据是形如 "[1]" 、 "[2]" 、 "[1,2]"
        //对取出的数据做处理
        string = string.replace("[", "");
        string = string.replace("]", "");
        String[] strings = string.split(",");

        List<String> permissionByRoleid = adminMapper.selectPermissionByRoleid(strings[0]);
        for (int i = 1; i < strings.length; i++) {
            permissionByRoleid.addAll(adminMapper.selectPermissionByRoleid(strings[i]));
        }
        //去重
        List<String> permissions = permissionByRoleid.stream().distinct().collect(Collectors.toList());
        for (String permission : permissions) {
            System.out.println(permission);
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permissions);
    }
}

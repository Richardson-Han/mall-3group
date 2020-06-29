package com.cskaoyan.mall;

import com.cskaoyan.mall.bean.Admin;
import com.cskaoyan.mall.mapper.AdminMapper;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * @author 韩
 * @create 2020-06-29 1:17
 */
@SpringBootTest
public class HanShiroTest {
    @Autowired
    AdminMapper adminMapper;


    /**
     * 登陆账户生成密码代码
     */
    @Test
    public void HanMD5PasswordTest() {
        /**
         * 超级管理员生成的密码
         */
        String username, password;
        Map<String, String> map = HanMD5Admin();
        username = map.get("username");
        password = map.get("password");
        String passwordDB = new Md5Hash(password, username + "3group", 8).toString();
        System.out.println("admin password = " + passwordDB);
        //更新代码 密码自动写入数据库
        Integer update = adminMapper.updateByUsername(username, passwordDB);
        System.out.println("admin update = " + update);
        System.out.println("'update = 1' is succeed");

        //d42dff1e2269d84af9f4defaa89d51d1
        /**
         * 推广管理员密码
         */
        map = HanMD5Promotion();
        username = map.get("username");
        password = map.get("password");
        passwordDB = new Md5Hash(password, username + "3group", 8).toString();
        System.out.println("Promotion password = " + passwordDB);
        update = adminMapper.updateByUsername(username, passwordDB);
        System.out.println("Promotion update = " + update);
        System.out.println("'update = 1' is succeed");

        /**
         * 商城管理员密码
         */
        map = HanMD5mall();
        username = map.get("username");
        password = map.get("password");
        passwordDB = new Md5Hash(password, username + "3group", 8).toString();
        System.out.println("mall password = " + passwordDB);
        update = adminMapper.updateByUsername(username, passwordDB);
        System.out.println("mall update = " + update);
        System.out.println("'update = 1' is succeed");
    }

    /**
     * 超级管理员账号密码
     */
    public Map<String, String> HanMD5Admin() {
        Map<String, String> admin = new HashMap<>();
        admin.put("username", "admin123");
        admin.put("password", "admin123");
        return admin;
    }

    /**
     * 商城管理员账号密码
     */
    public Map<String, String> HanMD5mall() {
        Map<String, String> mall = new HashMap<>();
        mall.put("username", "mall123");
        mall.put("password", "mall123");
        return mall;
    }

    /**
     * 推广管理员账号密码
     */
    public Map<String, String> HanMD5Promotion() {
        Map<String, String> promotion = new HashMap<>();
        promotion.put("username", "promotion123");
        promotion.put("password", "promotion123");
        return promotion;
    }



    /**
     * 权限测试相关
     */
    @Test
    public void hantest3() {
        String roleid = "2";
        List<String> permissionByRoleids = adminMapper.selectPermissionByRoleid(roleid);
        String permissionByRoleidstr = permissionByRoleids.toString();
        String turePermissionByRoleidstr = permissionByRoleidstr.replace(":", "/");
        turePermissionByRoleidstr = turePermissionByRoleidstr.replace("[", "");
        turePermissionByRoleidstr = turePermissionByRoleidstr.replace("]", "");
        turePermissionByRoleidstr = turePermissionByRoleidstr.replace(" ", "");
        String[] turePermissions = turePermissionByRoleidstr.split(",");
        LinkedHashMap<String, String> permissions = new LinkedHashMap<>();
        String perms = new String();
        for (String turePermission : turePermissions) {
            perms = "perms[" + turePermission.replace("/", ":") + "]";
            permissions.put(turePermission, perms);
        }
        Iterator<Map.Entry<String, String>> iterator = permissions.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            System.out.println(next.getKey() + " = " + next.getValue());
        }

        String[] strings = adminMapper.selectAllRoleid();
        System.out.println(strings[0]);

    }
}

package com.cskaoyan.mall;

import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.mapper.UserMapper;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 韩
 * @create 2020-06-29 23:29
 */
@SpringBootTest
public class WXHanShiroTest {

    @Autowired
    UserMapper userMapper;

    /**
     * 三个测试账户的重新加密写入
     */
    @Test
    public void WXHanMD5PasswordTest(){
        /**
         * test1
         */
        String username, password;
        Map<String, String> map = WXHanMD5test1();
        username = map.get("username");
        password = map.get("password");
        String passwordDB = new Md5Hash(password, username + "3groupWX", 3).toString();
        System.out.println("test1 password = " + passwordDB);
        //更新代码 密码自动写入数据库
        Integer update = userMapper.updatePasswordByUsername(username, passwordDB);
        System.out.println("test1 update = " + update);
        System.out.println("'update = 1' is succeed");


        /**
         * test2
         */
        map = WXHanMD5test2();
        username = map.get("username");
        password = map.get("password");
        passwordDB = new Md5Hash(password, username + "3groupWX", 3).toString();
        System.out.println("test2 password = " + passwordDB);
        update = userMapper.updatePasswordByUsername(username, passwordDB);
        System.out.println("test2 update = " + update);
        System.out.println("'update = 1' is succeed");

        /**
         * test3
         */
        map = WXHanMD5test3();
        username = map.get("username");
        password = map.get("password");
        passwordDB = new Md5Hash(password, username + "3groupWX", 3).toString();
        System.out.println("test3 password = " + passwordDB);
        update = userMapper.updatePasswordByUsername(username, passwordDB);
        System.out.println("test3 update = " + update);
        System.out.println("'update = 1' is succeed");
    }

    /**
     * test1的账号密码
     */
    public Map<String, String> WXHanMD5test1() {
        Map<String, String> admin = new HashMap<>();
        admin.put("username", "test1");
        admin.put("password", "test1");
        return admin;
    }

    /**
     * test2的账号密码
     */
    public Map<String, String> WXHanMD5test2() {
        Map<String, String> mall = new HashMap<>();
        mall.put("username", "test4");
        mall.put("password", "test4");
        return mall;
    }

    /**
     * tes3的账号密码
     */
    public Map<String, String> WXHanMD5test3() {
        Map<String, String> promotion = new HashMap<>();
        promotion.put("username", "test5");
        promotion.put("password", "test5");
        return promotion;
    }

    @Test
    public void mysqlconnectorTest(){
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user.getNickname());
    }


    @Test
    public void mysqlconnectorTest2(){
        User user = userMapper.selectByPrimaryKey(1);
        user.setUpdateTime(new Date());
        int update = userMapper.updateByPrimaryKey(user);
        System.out.println("update = " + update);
    }
}

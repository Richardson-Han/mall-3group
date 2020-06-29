package com.cskaoyan.mall;

import com.cskaoyan.mall.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SpringBootTest
class MallApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 测试获取系统时间
     */
    @Test
    public void test1(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String expireTime = simpleDateFormat.format(new Date());
        //System.out.println(expireTime);
        System.out.println(new Date());
    }

}

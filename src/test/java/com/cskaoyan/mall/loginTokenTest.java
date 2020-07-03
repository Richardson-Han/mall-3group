package com.cskaoyan.mall;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author 韩
 * @create 2020-07-01 13:08
 */
@SpringBootTest
public class loginTokenTest {
    @Value("歪~~~")
    String username;

    @Test
    public void hanTest(){
        System.out.println(username);

    }
}

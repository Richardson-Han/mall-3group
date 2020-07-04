package com.cskaoyan.mall;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 韩
 * @create 2020-07-01 19:22
 */
@SpringBootTest
public class HanHTTPresponseTest {
    @Test
    public void HanresponseCreateTest(){
        HttpServletResponse response;
        Class<?>[] classes = ServletResponse.class.getClasses();
    }
}

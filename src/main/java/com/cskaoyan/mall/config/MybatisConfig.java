package com.cskaoyan.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/* *
@author  Walker-胡
@create 2020-06-28 13:14
*/
//开启事务管理器 <tx:annotation-driven>
@Configuration
@EnableTransactionManagement
@MapperScan("com.cskaoyan.mall.mapper")
public class MybatisConfig {
}

package com.cskaoyan.mall.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 韩
 * @create 2020-06-29 1:17
 */
@ControllerAdvice
public class ExceptionControllerAdvise {

    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public String handlerAuthorException(Exception e){
        return "noperm";//ModelAndView
    }
}

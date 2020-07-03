package com.cskaoyan.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 韩
 * @create 2020-06-29 1:07
 */
@Controller
public class ShiroController {

    @RequestMapping(value = "/noperm")
    public String noperm() {
        return "noperm";
    }

}

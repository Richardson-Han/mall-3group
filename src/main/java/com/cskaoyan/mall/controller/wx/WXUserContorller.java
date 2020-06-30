package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.VO.BaseRespVo;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 韩
 * @create 2020-06-30 6:19
 */
@RestController
@RequestMapping("/wx/user")
public class WXUserContorller {


    @RequiresGuest
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public BaseRespVo index() {
        //{"timestamp":"2020-06-30T08:05:42.828+00:00","status":404,"error":"Not Found","message":"",
        // "path":"/wx/user/index"}
        System.out.println("???index??????********");

        //从token得到username
        //username得到user_id
        //user_id 得到order表对应值
        return BaseRespVo.ok();
    }
}

package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.AddressService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杨
 * @create 2020-06-30 15:13
 */
@RestController
@RequestMapping("wx/address")
public class WXAddressController {

    @Autowired
    AddressService addressService;

    @RequestMapping("list")
    public BaseRespVo wxAddressListGet(){
        Subject subject = SecurityUtils.getSubject();
        Object primaruPrincipal = subject.getPrincipals().getPrimaryPrincipal();
        return null;
    }
}

package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.Address;
import com.cskaoyan.mall.bean.BO.WXAddressSaveBO;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.VO.wx.WXAddressDetailVO;
import com.cskaoyan.mall.bean.VO.wx.WXAddressListVO;
import com.cskaoyan.mall.service.AddressService;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    public BaseRespVo wxAddressListGet() {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipals().getPrimaryPrincipal();
        List<WXAddressListVO> addressList = addressService.getAddressListByUsername(username);
        return BaseRespVo.ok(addressList);
    }

    @RequestMapping("detail")
    public BaseRespVo wxAddressListGet(Integer id) {
        WXAddressDetailVO addressDetail = addressService.selectAddressDetailById(id);
        if (addressDetail != null) {
            return BaseRespVo.ok(addressDetail);
        } else {
            return BaseRespVo.error();
        }
    }

    @PostMapping("save")
    public BaseRespVo wxAddressSave(@RequestBody Address addressBO) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipals().getPrimaryPrincipal();

        Integer id = addressService.saveAddress(addressBO, username);
        if (id > 0) {
            return BaseRespVo.ok(id);
        } else {
            return BaseRespVo.error();
        }
    }

    @PostMapping("delete")
    public BaseRespVo wxAddressDelete(@RequestBody Map map) {
        Integer id = (int)map.get("id");
        Integer result = addressService.deleteAddressById(id);
        if (result > 0) {
            return BaseRespVo.ok();
        } else {
            return BaseRespVo.error();
        }
    }

}

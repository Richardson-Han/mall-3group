package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.wx.WXFootprint;
import com.cskaoyan.mall.service.UserService;
import com.cskaoyan.mall.service.WXFootprintService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/* *
@author  Walker-胡
@create 2020-06-30 18:35
*/
@RestController
@RequestMapping("/wx/footprint")
@RequiresAuthentication
public class WXFootprintController {
    @Autowired
    WXFootprintService wxFootprintService;

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public BaseRespVo getWXFootprintList(@RequestParam("page") Integer page,
                                         @RequestParam("size") Integer size) {
        String  username = (String)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        Integer userId = userService.wxselectIdByUsername(username);
        System.out.println(userId);
        return BaseRespVo.ok(wxFootprintService.queryWXFootprintList(page, size,userId));
    }

    @PostMapping("/delete")
    public BaseRespVo deleteWXFootprint(@RequestBody WXFootprint wxFootprint) {
        if (wxFootprintService.deleteWXFootprint(wxFootprint.getId())) {
            return BaseRespVo.ok();
        }
        return BaseRespVo.error("删除失败", 603);
    }

}

package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.SearchService;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/***
 * @author 社会主义好
 * @date 2020-07-01 9:02 星期三 
 *
 */
@RestController
@RequestMapping("wx/search")
public class WXSearchController {

    @Autowired
    SearchService searchService;

    @RequiresAuthentication
    @RequestMapping("index")
    public BaseRespVo index() {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Map<String, Object> map = searchService.index(username);
        return BaseRespVo.ok(map);
    }

    @RequiresAuthentication
    @RequestMapping("clearhistory")
    public BaseRespVo clearhistory() {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        searchService.clearhistory(username);
        return BaseRespVo.ok();
    }

    @RequestMapping("helper")
    public BaseRespVo helper(String keyword) {
        List<String> data = searchService.helper(keyword);
        return BaseRespVo.ok(data);
    }
}

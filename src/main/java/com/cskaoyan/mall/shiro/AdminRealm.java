package com.cskaoyan.mall.shiro;

import com.cskaoyan.mall.service.AdminService;
import com.cskaoyan.mall.utils.CharacterArrayConversionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 韩
 * @create 2020-06-28 20:45
 */
@Component
public class AdminRealm extends AuthorizingRealm {


    @Autowired
    AdminService adminService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        List<String> strings = adminService.selectPasswordByName(username);
        String credential = strings.size() >= 1 ? strings.get(0) : null;
        return new SimpleAuthenticationInfo(username, credential, this.getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        String string = adminService.selectRoleidByUsername(username);
        //取出的数据是形如 "[1]" 、 "[2]" 、 "[1,2]"
        //对取出的数据做处理
        String[] strings = CharacterArrayConversionUtils.stringConvertsAnArrayOfStrings(string);

        List<String> permissionByRoleid = Arrays.asList(adminService.selectPermissionByRoleid(strings[0]));
        for (int i = 1; i < strings.length; i++) {
            Collections.addAll(permissionByRoleid, adminService.selectPermissionByRoleid(strings[i]));
        }
        //去重
        List<String> permissions = permissionByRoleid.stream().distinct().collect(Collectors.toList());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //注册权限
        authorizationInfo.addStringPermissions(permissions);

        return authorizationInfo;
    }
}

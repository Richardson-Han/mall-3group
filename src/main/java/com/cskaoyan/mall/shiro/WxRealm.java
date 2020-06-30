package com.cskaoyan.mall.shiro;

import com.cskaoyan.mall.mapper.UserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 韩
 * @create 2020-06-29 4:52
 */
@Component
public class WxRealm extends AuthorizingRealm {

    @Autowired
    UserMapper userMapper;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        MallToken token = (MallToken) authenticationToken;
        String type = token.getType();
        return dealInfoByType(type);
    }

    /**
     * 先全部允许
     */
    private AuthenticationInfo dealInfoByType(String type) {
        //根据info不同处理不同的认证信息
        return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        List<String> permission = new ArrayList<>();
        permission.add("wxAll");//先用全部权限顶一顶

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permission);
        return authorizationInfo;
    }
}

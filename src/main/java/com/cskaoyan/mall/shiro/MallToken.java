package com.cskaoyan.mall.shiro;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author 韩
 * @create 2020-06-29 11:24
 */
@Data
public class MallToken extends UsernamePasswordToken {
    String type;

    public MallToken(String username, String password, String type) {
        super(username, password);
        this.type = type;
    }
}

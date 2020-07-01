package com.cskaoyan.mall.bean.VO.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author 杨
 * @create 2020-07-01 9:06
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WXUserLoginVO {

    String token;

    Date tokenExpire;

    WXUserInfoVO userInfo;
}

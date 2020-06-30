package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.VO.BaseRespVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author 韩
 * @create 2020-06-30 6:19
 */
@Controller
@RequestMapping("/wx/user")
public class WXUserContorller {


    @RequestMapping("index")
    public BaseRespVo index(Map map){
        String  token = (String) map.get("X-Litemall-Token");
        //从token得到username
        //username得到user_id
        //user_id 得到order表对应值
        return BaseRespVo.ok();
    }
}

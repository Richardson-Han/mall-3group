package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.VO.wx.WXUserOrderVO;
import com.cskaoyan.mall.bean.wx.WXOrderState;
import com.cskaoyan.mall.mapper.OrderMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.UserService;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 韩
 * @create 2020-06-30 6:19
 */
@RestController
@RequestMapping("/wx/user")
public class WXUserContorller {

    @Autowired
    UserService userService;
    @Autowired
    OrderMapper orderMapper;


    @RequiresGuest
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public BaseRespVo index(HttpServletRequest request) {
        // {"errno":0,"data":{"order":{"unrecv":4,"uncomment":0,"unpaid":0,"unship":0}},"errmsg":"成功"}

        HttpSession session = request.getSession();
        //从token得到username
        String token = request.getHeader("X-cskaoyan-mall-Admin-Token");
        //shiro 未完成 无法获得其他token值比较获取真正当前登陆用户
        if ("j65dcjj0if0tf223567uwgu9a7t7b1z8".equals(token)) {
            String username = "test1";
            //username得到user_id
            Integer userId = userService.wxselectIdByUsername(username);
            //user_id 得到order表对应值
            WXOrderState wxOrder = new WXOrderState();
            wxOrder.setUnrecv(orderMapper.selectUnrecvByUserId(userId));
            Integer Uncomment = orderMapper.selectUncommentByUserId(userId);
            wxOrder.setUncomment(Uncomment == null ? 0 : Uncomment);
            wxOrder.setUnpaid(orderMapper.selectUnpaidByUserId(userId));
            wxOrder.setUnship(orderMapper.selectUnshipByUserId(userId));
            WXUserOrderVO order = new WXUserOrderVO(wxOrder);
            return BaseRespVo.ok(order);
        } else {
            return BaseRespVo.error();
        }
    }
}

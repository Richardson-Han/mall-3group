package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.VO.wx.WXUserOrderVO;
import com.cskaoyan.mall.bean.wx.WXOrderState;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.service.UserService;
import com.cskaoyan.mall.utils.WXTokenUtils;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    OrderService orderService;

    String error = "this token is error";

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public BaseRespVo index(HttpServletRequest request) {
        // {"errno":0,"data":{"order":{"unrecv":4,"uncomment":0,"unpaid":0,"unship":0}},"errmsg":"成功"}

        String username = WXTokenUtils.requestToUsername(request);
        if (error.equals(username)){
            return BaseRespVo.error("请先登陆");
        }
        //username得到user_id
        Integer userId = userService.wxselectIdByUsername(username);
        //user_id 得到order表对应值
        WXOrderState wxOrder = new WXOrderState();
        wxOrder.setUnrecv(orderService.wxselectUnrecvByUserId(userId));
        Integer Uncomment = orderService.wxselectUncommentByUserId(userId);
        wxOrder.setUncomment(Uncomment == null ? 0 : Uncomment);
        wxOrder.setUnpaid(orderService.wxselectUnpaidByUserId(userId));
        wxOrder.setUnship(orderService.wxselectUnshipByUserId(userId));
        WXUserOrderVO order = new WXUserOrderVO(wxOrder);
        return BaseRespVo.ok(order);
    }
}

package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.wx.BO.CartCheckBO;
import com.cskaoyan.mall.bean.wx.BO.CartCheckoutBO;
import com.cskaoyan.mall.bean.wx.BO.CartUpdateBO;
import com.cskaoyan.mall.bean.wx.VO.CartCheckoutVO;
import com.cskaoyan.mall.bean.wx.VO.CartListVO;
import com.cskaoyan.mall.service.CartService;
import com.cskaoyan.mall.utils.WXTokenUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Fang
 * @create 2020/6/30-16:34
 */
@RestController
@RequestMapping("wx/cart")
public class WXCartController {
    @Autowired
    CartService cartService;

    String error = "this token is error";

    @RequiresAuthentication
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public BaseRespVo index(HttpServletRequest request){
        String username = WXTokenUtils.requestToUsername(request);
        if (error.equals(username)) {
            return BaseRespVo.error("请先登陆");
        }
        CartListVO cartListVO = cartService.queryListByUsername(username);
        return BaseRespVo.ok(cartListVO);
    }

    @RequiresAuthentication
    @RequestMapping("goodscount")
    public BaseRespVo goodscount(HttpServletRequest request){
        String username = WXTokenUtils.requestToUsername(request);
        Integer goodScount= cartService.goodscount(username);
        return BaseRespVo.ok(goodScount);
    }

    @RequiresAuthentication
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public BaseRespVo add(@RequestBody Map map, HttpServletRequest request){
        String username = WXTokenUtils.requestToUsername(request);
        if (error.equals(username)) {
            return BaseRespVo.error("请先登陆");
        }
        //加入购入车判断库存是否足够
        //返回购物车商品的总数
        Integer goodsCount = cartService.add(map, username);
        if(goodsCount == null){
            return BaseRespVo.error("库存不够");
        }
        return BaseRespVo.ok(goodsCount);
    }

    @RequiresAuthentication
    @RequestMapping("checked")
    public BaseRespVo checked(@RequestBody CartCheckBO cartCheckBO, HttpServletRequest request){
        String username = WXTokenUtils.requestToUsername(request);
        CartListVO cartListVO = cartService.checked(cartCheckBO, username);
        return BaseRespVo.ok(cartListVO);
    }

    @RequiresAuthentication
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody CartUpdateBO cartUpdateBO){
        //String username = WXTokenUtils.requestToUsername(request);
        cartService.update(cartUpdateBO);
        return BaseRespVo.ok();
    }

    @RequiresAuthentication
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Map map, HttpServletRequest request){
        String username = WXTokenUtils.requestToUsername(request);
        List<Integer> productIds = (List<Integer>) map.get("productIds");
        CartListVO cartListVO = cartService.delete(productIds, username);
        return BaseRespVo.ok(cartListVO);
    }

    @RequiresAuthentication
    @RequestMapping("checkout")
    public BaseRespVo checkout(CartCheckoutBO checkoutBO, HttpServletRequest request){
        String username = WXTokenUtils.requestToUsername(request);
        CartCheckoutVO checkout = cartService.checkout(checkoutBO, username);
        return BaseRespVo.ok(checkout);
    }

    @RequiresAuthentication
    @RequestMapping("fastadd")
    public BaseRespVo fastadd(@RequestBody Map map, HttpServletRequest request){
        String username = WXTokenUtils.requestToUsername(request);
        if (error.equals(username)) {
            return BaseRespVo.error("请先登陆");
        }
        //返回一个新生成的商品订单的id
        Integer newOrderGoodsId = cartService.fastadd(map, username);
        return BaseRespVo.ok(newOrderGoodsId);
    }
}

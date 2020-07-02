package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.wx.BO.CartCheckBO;
import com.cskaoyan.mall.bean.wx.BO.CartCheckoutBO;
import com.cskaoyan.mall.bean.wx.BO.CartUpdateBO;
import com.cskaoyan.mall.bean.wx.VO.CartCheckoutVO;
import com.cskaoyan.mall.bean.wx.VO.CartListVO;
import com.cskaoyan.mall.bean.wx.VO.CartTotalVO;
import com.cskaoyan.mall.mapper.*;
import com.cskaoyan.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Fang
 * @create 2020/7/1-16:51
 */

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    GroupOnMapper groupOnMapper;
    @Autowired
    GoodsMapper  goodsMapper;
    @Autowired
    GoodsProductMapper productMapper;
    @Autowired
    OrderGoodsMapper orderGoodsMapper;


    @Override
    public CartListVO queryListByUsername(String username) {
        //根据username来获取对应的userId
        Integer userId = userMapper.selectIdByUsername(username);
        //根据userId来获取用户的购物车信息
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        List<Cart> cartList = cartMapper.selectByExample(cartExample);
       /* Double goodsAmount = 0.0;
        int goodsCount = 0;
        for (Cart cart : cartList) {
            BigDecimal number = new BigDecimal(cart.getNumber());
            BigDecimal price = cart.getPrice();
            goodsAmount = goodsAmount + (price.multiply(number)).doubleValue();
            goodsCount += cart.getNumber();
        }*/
        CartTotalVO cartTotalVO = cartTotal(cartList);

        return new CartListVO(cartList, cartTotalVO);
    }

    /**
     *  统计购物车商品数量
     */
    @Override
    public Integer goodscount(String username) {
        //根据username来获取对应的userId
        Integer userId = userMapper.selectIdByUsername(username);
        //根据userId来获取用户的购物车信息
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        List<Cart> cartList = cartMapper.selectByExample(cartExample);
        //统计总数
        Integer goodsCount = 0;
        for (Cart cart : cartList) {
            goodsCount += cart.getNumber();
        }
        return goodsCount;
    }

    /**
     *  add
     *  添加商品到购物车，并返回总的购物车商品总数
     */
    @Override
    public Integer add(Map map, String username) {
        //根据username来获取对应的userId
        Integer userId = userMapper.selectIdByUsername(username);
        //获得加入购物车的商品数量
        //Integer number = (Integer) map.get("number");
        //number.shortValue();
        Short number = ((Integer) map.get("number")).shortValue();
        //获得productId
        Integer productId = (Integer) map.get("productId");
        //根绝productId去获得goods_product表中对应的信息
        GoodsProduct goodsProduct = productMapper.selectByPrimaryKey(productId);
        //获得库存并与number比较
        if(number > goodsProduct.getNumber()){
            return null;
        }
        //获取goodsId
        Integer goodsId = (Integer) map.get("goodsId");
        //再来获得goods信息
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);


        //放入cart需要的goods信息
        //价格是 现价 retailPrice
        Cart addCart = new Cart(null, userId, goodsId, goodsId.toString(), goods.getName(), productId, goodsProduct.getPrice(),
                number, goodsProduct.getSpecifications(), false, goodsProduct.getUrl(), new Date(), new Date(), false);

        cartMapper.insertSelective(addCart);

        //根据userId来获取用户的购物车信息
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        List<Cart> cartList = cartMapper.selectByExample(cartExample);
        //统计总数
        Integer goodsCount = 0;
        for (Cart cart : cartList) {
            goodsCount += cart.getNumber();
        }
        return goodsCount;
    }

    @Override
    public CartListVO checked(CartCheckBO cartCheckBO, String username){
        //根据username来获取对应的userId
        Integer userId = userMapper.selectIdByUsername(username);
        Integer checked = cartCheckBO.getIsChecked();
        List<Integer> productIds = cartCheckBO.getProductIds();

        for (Integer productId : productIds) {
            cartMapper.updateCheckedByIdAndUserId(productId, checked, userId);
            //通过productId字段去查找对应的cart
           // Cart cart = cartMapper.selectByProductIdAndUserId(productId ,userId);
        }

        //不能只显示选择的那个，要全部显示出来
        //选中哪个只是为了改变其checked值
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        List<Cart> cartList = cartMapper.selectByExample(cartExample);
        CartTotalVO cartTotalVO = cartTotal(cartList );
        return new CartListVO(cartList, cartTotalVO);
    }

    /**
     *  uodate
     * @param cartUpdateBO
     */
    @Override
    public void update(CartUpdateBO cartUpdateBO) {
        Integer id = cartUpdateBO.getId();
        Integer number = cartUpdateBO.getNumber();
        cartMapper.updateNumberById(id,number);
    }

    /**
     *  delete
     */
    @Override
    public CartListVO delete(List<Integer> productIds, String username) {
        //根据username来获取对应的userId
        Integer userId = userMapper.selectIdByUsername(username);
        for (Integer productId : productIds) {
            cartMapper.updateDeletedByProductIdAndUserId(userId, productId);
        }

        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        List<Cart> cartList = cartMapper.selectByExample(cartExample);

        CartTotalVO cartTotalVO = cartTotal(cartList);

        return new CartListVO(cartList, cartTotalVO);
    }

    /**
     *  checkout
     */
    @Override
    public CartCheckoutVO checkout(CartCheckoutBO checkoutBO, String username) {
        //根据username来获取对应的userId
        Integer userId = userMapper.selectIdByUsername(username);
        //计算选中的商品的总价
        Double goodsTotalprice = 0.0;
        //若cartId不为0，则表示是fastadd，即直接购买的
        Integer cartId = checkoutBO.getCartId();
        CartExample cartExample = new CartExample();
        if(cartId != 0){
            //Cart cart = cartMapper.selectByPrimaryKey(cartId);
            //不要上面这么写， 因为返回的json数据中，cart还是以一个对象来存在数组中的
            cartExample.createCriteria().andIdEqualTo(cartId);
            //goodsTotalprice = cart.getPrice().multiply(new BigDecimal(cart.getNumber())).doubleValue();
        }else {
            //若cartId为0，表示是从购物车来购买的
            //根据userID，找到对应的选中的商品，即checked = 1
            cartExample.createCriteria().andUserIdEqualTo(userId).andCheckedEqualTo(true);
        }
        List<Cart> checkedGoodsList = cartMapper.selectByExample(cartExample);
        for (Cart cart : checkedGoodsList) {
            BigDecimal number = new BigDecimal(cart.getNumber());
            BigDecimal price = cart.getPrice();
            goodsTotalprice += price.multiply(number).doubleValue();
        }

        //根据选中的优惠券id来获取其优惠价格
        Double actualPrice = goodsTotalprice;
        Double orderTotalPrice = goodsTotalprice;
        Integer couponId = checkoutBO.getCouponId();
        Double couponPrice = 0.0;
        if(couponId <= 0){
            couponPrice = 0.0;
        }else {
            //根据其couponId来获取其一条数据
            Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
            couponPrice = coupon.getDiscount().doubleValue();
            if(goodsTotalprice >= coupon.getLimit()){
                //优惠价格
                actualPrice =  goodsTotalprice - couponPrice;
                orderTotalPrice = goodsTotalprice - couponPrice;
            }
        }
        //优惠券数量
        Integer availableCouponLength = couponMapper.selectCountNumber();

        //根据地址id去获得地址信息
        Address checkedAddress;
        Integer addressId = checkoutBO.getAddressId();
        if(addressId == 0){
            checkedAddress = new Address(0);
        }else {
            checkedAddress = addressMapper.selectByPrimaryKey(addressId);
        }

        //根据组团id获得其信息
        Integer grouponRulesId = checkoutBO.getGrouponRulesId();
        //组团优惠， 逻辑没有理清楚
        Double grouponPrice = 0.0;
        if(grouponRulesId == 0){
            grouponPrice = 0.0;
        }else {
            //该逻辑没有理清楚
            grouponPrice = 0.0;
        }

        //运费设置为0
        Integer freightPrice = 0;

        CartCheckoutVO cartCheckoutVO = new CartCheckoutVO(actualPrice, addressId, availableCouponLength, checkedAddress, checkedGoodsList,
                couponId, couponPrice, freightPrice, goodsTotalprice, grouponPrice, grouponRulesId, orderTotalPrice);
        return cartCheckoutVO;
    }

    /**
     *  直接购买生成新商品订单
     */
    @Override
    public Integer fastadd(Map map, String username) {
        //根据username来获取对应的userId
        //好像username在这里没什么用。。。
        Integer userId = userMapper.selectIdByUsername(username);

        //获得加入购物车的商品数量
        Integer number = (Integer) map.get("number");
        //获得productId
        Integer productId = (Integer) map.get("productId");
        //根绝productId去获得goods_product表中对应的信息
        GoodsProduct goodsProduct = productMapper.selectByPrimaryKey(productId);
        //获取goodsId
        Integer goodsId = (Integer) map.get("goodsId");
        //再来获得goods信息
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        //新建一个商品订单
        OrderGoods orderGoods = new OrderGoods(null, null, goodsId, goods.getName(), goodsId.toString(), productId, (short)number.intValue(), goodsProduct.getPrice(),
                goodsProduct.getSpecifications(), goodsProduct.getUrl(), null, new Date(), new Date(), false);

        orderGoodsMapper.insertSelective(orderGoods);
        Integer id = orderGoods.getId();
        return id;
    }

    /**
     *  计算  cartTotal
     */
    private CartTotalVO cartTotal(List<Cart> cartList){
        Double checkedgoodsAmount = 0.0;
        int checkedgoodsCount = 0;
        Double goodsAmount = 0.0;
        int goodsCount = 0;
        for (Cart cart : cartList) {
            BigDecimal number = new BigDecimal(cart.getNumber());
            BigDecimal price = cart.getPrice();
            //checked为1
            if(cart.getChecked() == true){
                checkedgoodsAmount += price.multiply(number).doubleValue();
                checkedgoodsCount += cart.getNumber();
            }
            goodsAmount = goodsAmount + (price.multiply(number)).doubleValue();
            goodsCount += cart.getNumber();
        }
        return new CartTotalVO(checkedgoodsAmount, checkedgoodsCount, goodsAmount, goodsCount);
    }
}

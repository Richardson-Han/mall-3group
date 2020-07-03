package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.System;
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
    @Autowired
    CouponUserMapper couponUserMapper;
    @Autowired
    SystemMapper systemMapper;


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

        //如果添加的商品在购物车中已存在，则合并
        Cart cart1 = cartMapper.selectByUserIdAndGoodsIdAndProductId(userId, goodsId, productId);
        if(cart1 != null){
            cart1.setNumber((short) (cart1.getNumber() + number));
            cartMapper.updateByPrimaryKeySelective(cart1);
        }else {

            //放入cart需要的goods信息
            //价格是 现价 retailPrice
            Cart addCart = new Cart(null, userId, goodsId, goodsId.toString(), goods.getName(), productId, goodsProduct.getPrice(),
                    number, goodsProduct.getSpecifications(), false, goodsProduct.getUrl(), new Date(), new Date(), false);

            cartMapper.insertSelective(addCart);
        }

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
     *  checked
     */
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
            cartExample.createCriteria().andUserIdEqualTo(userId).andCheckedEqualTo(true).andDeletedEqualTo(false);
        }
        List<Cart> checkedGoodsList = cartMapper.selectByExample(cartExample);
        //获取所有的goods信息
        List<Goods> goodsList = new ArrayList<>();
        for (Cart cart : checkedGoodsList) {
            BigDecimal number = new BigDecimal(cart.getNumber());
            BigDecimal price = cart.getPrice();
            goodsTotalprice += price.multiply(number).doubleValue();
            Integer goodsId = cart.getGoodsId();
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            goodsList.add(goods);
        }

        //根据选中的优惠券id来获取其优惠价格
        Double actualPrice = goodsTotalprice;
        Double orderTotalPrice = goodsTotalprice;
        //优惠券id
        Integer couponId = checkoutBO.getCouponId();
        //优惠券减少的金额
        Double couponPrice = 0.0;
        //获取已拥有的优惠券, 查看coupon_user
        CouponUserExample couponUserExample = new CouponUserExample();
        couponUserExample.createCriteria().andDeletedEqualTo(false).andStatusEqualTo((short) 0).andUserIdEqualTo(userId);
        List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);
        //在获取全部的coupon
        List<Coupon> coupons = new ArrayList<>();
        for (CouponUser couponUser : couponUsers) {
            //应该在这里就筛选
            //根据couponId去找对应的优惠券，其最低的消费 <= 商品总金额
            //Coupon coupon = couponMapper.selectByPrimaryKey(couponUser.getCouponId());
            Coupon coupon = couponMapper.selectByIdAndMin(couponUser.getCouponId(), new BigDecimal(goodsTotalprice));
            //要对coupon进行判断，为null不被添加, 不然会出现 空指针异常
            if(coupon != null){
                coupons.add(coupon);
            }
        }
        //根据couponId来判断是  自动选择 还是 自定义选择 优惠券
        //下面代码写的太辣鸡了，能力有限
        if(couponId <= 0){
            if(coupons.size() == 0){
                //若为null，则无优惠
                couponPrice = 0.0;
            }else {
                for (Coupon coupon : coupons) {
                    //查看其优惠券类型
                    Short type = coupon.getType();
                    if(type == 2){
                        //兑换码优惠券
                        if(coupon.getCode() == null){
                            couponPrice = 0.0;
                        }else {
                            //假设没有商品限制
                            //因为type=2，是兑换码优惠券，limit肯定为1
                            if(coupon.getDiscount().doubleValue() >= couponPrice){
                                couponId = coupon.getId();
                                couponPrice = coupon.getDiscount().doubleValue();
                            }
                        }
                    }else if(type == 0){
                        //优惠券类型为用户领取
                        //假如优惠券没有限制条件
                        //假设limit为1
                        Short limit = coupon.getLimit();
                        if(limit == 1) {
                            if(coupon.getDiscount().doubleValue() >= couponPrice){
                                couponId = coupon.getId();
                                couponPrice = coupon.getDiscount().doubleValue();
                            }
                        }else {
                            //此时limit为0，表示可无限制领券
                        }
                    } else if(type == 1){
                        //优惠券类型为 注册赠送券, 所以不能自己领，limit只能是1
                        //假如优惠券没有限制条件
                        if(coupon.getDiscount().doubleValue() >= couponPrice){
                            couponId = coupon.getId();
                            couponPrice = coupon.getDiscount().doubleValue();
                        }
                    }
                }//foreach
            }//coupons 不为null
            actualPrice =  goodsTotalprice - couponPrice;
            orderTotalPrice = goodsTotalprice - couponPrice;
        }else {
            //根据其couponId来获取其一条数据
            Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
            //判断该优惠券是否在 有效的优惠券中
            //boolean contains = coupons.contains(coupon);
            for (Coupon coupon1 : coupons) {
                if(coupon1.getId().equals(coupon.getId())){
                    if (goodsTotalprice >= coupon.getMin().doubleValue()) {
                        //优惠价格
                        actualPrice = goodsTotalprice - couponPrice;
                        orderTotalPrice = goodsTotalprice - couponPrice;
                        couponId = coupon.getId();
                        couponPrice = coupon.getDiscount().doubleValue();
                        break;
                    }
                }
            }
        }
        //有效优惠券数量
        Integer availableCouponLength = coupons.size();

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
            //
            grouponPrice = 0.0;
        }

        //运费去比较system表中的字段
        //若>=cskaoyan_mall_express_freight_min，则包邮，不然邮费为cskaoyan_mall_express_freight_value
        Integer freightPrice = 0;
        Integer systemFreightMin = 0;
        Integer systemFreightValue = 0;
        SystemExample systemExample = new SystemExample();
        List<System> systems = systemMapper.selectByExample(systemExample);
        for (System system : systems) {
            if(system.getKeyName().equals("cskaoyan_mall_express_freight_min")){
                systemFreightMin = Integer.parseInt(system.getKeyValue());
            }
            if(system.getKeyName().equals("cskaoyan_mall_express_freight_value")){
                systemFreightValue = Integer.parseInt(system.getKeyValue());
            }
        }
        if(goodsTotalprice >= systemFreightMin){
            freightPrice = 0;
        }else
            freightPrice = systemFreightValue;

        orderTotalPrice = orderTotalPrice + freightPrice;
        actualPrice = orderTotalPrice;
        CartCheckoutVO cartCheckoutVO = new CartCheckoutVO(actualPrice, addressId, availableCouponLength, checkedAddress, checkedGoodsList,
                couponId, couponPrice, freightPrice, goodsTotalprice, grouponPrice, grouponRulesId, orderTotalPrice);
        return cartCheckoutVO;
    }


    /**
     *  fastadd
     *  直接购买 生成 新cart
     *  deleted 为true ,不显示
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
        //新建一个cart
        Cart cart = new Cart(null, userId, goodsId, goodsId.toString(), goods.getName(), productId, goodsProduct.getPrice(),
                (short)number.intValue(), goodsProduct.getSpecifications(), false, goodsProduct.getUrl(), new Date(), new Date(), true);
        cartMapper.insertSelective(cart);
        Integer id = cart.getId();
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

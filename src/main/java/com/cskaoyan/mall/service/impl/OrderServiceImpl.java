package com.cskaoyan.mall.service.impl;


import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.System;
import com.cskaoyan.mall.bean.VO.OrderRefundVO;
import com.cskaoyan.mall.bean.VO.OrderStatusVO;
import com.cskaoyan.mall.bean.VO.ShipVO;
import com.cskaoyan.mall.bean.VO.StatBaseVO;
import com.cskaoyan.mall.bean.wx.HandleOption;
import com.cskaoyan.mall.bean.wx.VO.OrderSubmitVO;
import com.cskaoyan.mall.bean.wx.VO.WXOrderInfoVO;
import com.cskaoyan.mall.bean.wx.WXOrderGoods;
import com.cskaoyan.mall.mapper.*;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.utils.WxHandleOptionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 * @author 社会主义好
 * @date 2020-06-26 17:21 星期五 
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    /**
     *
     * @return 首页返回订单数量
     */
    @Override
    public Long getOrderTotal() {
        return orderMapper.countByExample(new OrderExample ());
    }

/*
* 显示所有订单详情
* */
    @Override
    public Map<String, Object> queryOrderPageList(Integer page, Integer limit, String sort, String order,
                                                  List<Integer> orderStatusArray, Integer userId, Integer orderSn) {
        Map<String, Object> map = new HashMap<> ();
        PageHelper.startPage (page, limit);
        List<Order> orders = orderMapper.queryOrderPageList (orderStatusArray, userId, orderSn, sort, order);
        PageInfo pageInfo = new PageInfo (orders);
        long total = pageInfo.getTotal ();
        map.put ("total", total);
        map.put ("items", orders);
        return map;
    }

    /**
     * 统计报表之订单统计
     * @return
     */
    @Override
    public StatBaseVO getOrderStat() {
        StatBaseVO statOrderVO = new StatBaseVO();
        ArrayList<String> columns = new ArrayList<>();
        columns.add("day");
        columns.add("orders");
        columns.add("customers");
        columns.add("amount");
        columns.add("pcr");
        statOrderVO.setColumns(columns);

        List<OrderStat> orderStats = orderMapper.selectGroupByAddTime();
        statOrderVO.setRows(orderStats);
        return  statOrderVO;
    }

    @Override
    public Map<String, Object> queryOrderDetailById(Integer id) {
        Map<String ,Object> map =new HashMap<> ();
        //查询订单表
        Order order = orderMapper.selectByPrimaryKey (id);
        //查询用户表
        User user=userMapper.selectByPrimaryKey (order.getUserId ());
        //查寻cskaoyanmall_order_goods表
        OrderGoods goods=orderGoodsMapper.selectByPrimaryKey (id);
        List<OrderGoods> orderGoods=new ArrayList<> ();
        orderGoods.add (goods);

        map.put ("order",order);
        map.put("user",user);
        map.put ("orderGoods",orderGoods);
        return map;
    }

    @Override
    public void orderRefund(OrderRefundVO orderRefundVO) {
        Order order = orderMapper.selectByPrimaryKey(orderRefundVO.getOrderId());
        order.setUpdateTime(new Date ());
        order.setOrderStatus(OrderStatusVO.user_cancelled);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void orderShip(ShipVO shipVO) {
        Order order = orderMapper.selectByPrimaryKey (shipVO.getOrderId ());
        order.setUpdateTime (new Date ());
        order.setOrderStatus (OrderStatusVO.order_delivered);
    }

    @Override
    public Integer wxselectUnrecvByUserId(Integer userId) {
        return orderMapper.selectUnrecvByUserId(userId);
    }

    @Override
    public Integer wxselectUncommentByUserId(Integer userId) {
        return orderMapper.selectUncommentByUserId(userId);
    }

    @Override
    public Integer wxselectUnpaidByUserId(Integer userId) {
        return orderMapper.selectUnpaidByUserId(userId);
    }

    @Override
    public Integer wxselectUnshipByUserId(Integer userId) {
        return orderMapper.selectUnshipByUserId(userId);
    }

    @Override
    public Integer queryGoodsIdByOrderId(Integer orderGoodsId) {
        Integer id = orderGoodsMapper.queryGoodsIdByOrderId(orderGoodsId);
        return id;
    }

    @Override
    public void updateCommentId(Integer orderGoodsId, Integer commentId) {
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setId(orderGoodsId);
        orderGoods.setComment(commentId);
        orderGoodsMapper.updateByPrimaryKeySelective(orderGoods);
    }

    @Override
    public List<Order> queryOrderByOrderStatus(Integer showType) {
        //判断订单状态
        if(showType == 0){
            List<Order> orders = orderMapper.selectAllOrders();
            return orders;
        }
        int status = 0;
        switch (showType){
            case 1 :
                status = 101;
                break;
            case 2 :
                status = 201;
                break;
            case 3 :
                status = 301;
                break;
            case 4 :
                status = 401;
                break;
        }
        List<Order> orders =  orderMapper.selectByOrderStatus(status);
        //待评价的订单需要另加判断
        if(showType == 4){
            ArrayList<Order> ordersfor4 = new ArrayList<Order>();
            for (Order order : orders) {
                if(order.getComments() != 0){
                    ordersfor4.add(order);
                }
            }
            return ordersfor4;
        }
        return orders;
    }

    @Override
    public List<WXOrderGoods> queryOrderGoodsByOrderId(Integer id) {
        List<WXOrderGoods> WXOrderGoodsList = orderGoodsMapper.selectByOrderId(id);
        return WXOrderGoodsList;
    }

    @Override
    public List<OrderGoods> selectOrderGoodsByOrderId(Integer orderId) {
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        orderGoodsExample.createCriteria().andOrderIdEqualTo(orderId);
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        return orderGoods;
    }

    @Override
    public WXOrderInfoVO getWxOrderInfo(Integer orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        WXOrderInfoVO wxOrderInfoVO = new WXOrderInfoVO();
        wxOrderInfoVO.setActualPrice(order.getActualPrice());
        wxOrderInfoVO.setAddTime(order.getAddTime());
        wxOrderInfoVO.setAddress(order.getAddress());
        wxOrderInfoVO.setConsignee(order.getConsignee());
        wxOrderInfoVO.setCouponPrice(order.getCouponPrice());
        wxOrderInfoVO.setFreightPrice(order.getFreightPrice());
        wxOrderInfoVO.setGoodsPrice(order.getGoodsPrice());
        HandleOption handleOption = WxHandleOptionUtil.getHandleOption(order.getOrderStatus());
        wxOrderInfoVO.setHandleOption(handleOption);
        wxOrderInfoVO.setId(order.getId());
        wxOrderInfoVO.setMobile(order.getMobile());
        wxOrderInfoVO.setOrderSn(order.getOrderSn());
        wxOrderInfoVO.setOrderStatusText(order.getOrderStatus());
        return wxOrderInfoVO;
    }

    @Override
    public void cancelOrder(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setOrderStatus((short) 102);
        order.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setUpdateTime(new Date());
        order.setDeleted(true);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void refund(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setUpdateTime(new Date ());
        order.setOrderStatus(OrderStatusVO.request_refund);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void confirmOrder(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setUpdateTime(new Date ());
        order.setOrderStatus(OrderStatusVO.user_receipt);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public OrderGoods getOrderGoods(Integer orderId, Integer goodsId) {
        OrderGoodsExample example = new OrderGoodsExample();
        example.createCriteria().andOrderIdEqualTo(orderId).andGoodsIdEqualTo(goodsId);
        List<OrderGoods> orderGoodsList = orderGoodsMapper.selectByExample(example);
        //只有一条数据
        return orderGoodsList.get(0);
    }




    //方惠
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    RegionMapper regionMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    GoodsProductMapper productMapper;
    @Autowired
    SystemMapper systemMapper;

    /**
     *  提交订单，返回订单号
     *  要修改库存，以及商品订单表的order_id
     *  还需要在orders_goods表新插入一条数据
     */
    @Override
    public OrderSubmitVO submit(Map map, String username) {
        //根据username来获取对应的userId
        Integer userId = userMapper.selectIdByUsername(username);
        //获取地址id
        Integer addressId = (Integer) map.get("addressId");
        //获取cartId
        Integer cartId = (Integer) map.get("cartId");
        //获取优惠券id
        Integer couponId = (Integer) map.get("couponId");
        //获取组团信息，先不管这个
        Integer grouponLinkId = (Integer) map.get("grouponLinkId");
        Integer grouponRulesId = (Integer) map.get("grouponRulesId");
        //获取备注信息
        String message = (String) map.get("message");

        CartExample cartExample = new CartExample();
        if(cartId == 0){
            cartExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userId).andCheckedEqualTo(true);
        }else {
            cartExample.createCriteria().andIdEqualTo(cartId);
        }
        List<Cart> cartList = cartMapper.selectByExample(cartExample);


        //订单号的生成，时间日期+随机数
        String orderSn = createOrderSn();
        //订单状态，未付款
        Short orderStatus = 101;
        //获取签收人
        Address address = addressMapper.selectByPrimaryKey(addressId);
        String consignee = address.getName();
        //获取电话号码
        String mobile = address.getMobile();
        //获取地址，包括省份，城市， 区域
        Integer areaId = address.getAreaId();
        Region areaRegion = regionMapper.selectByPrimaryKey(areaId);
        //区域名
        String area = areaRegion.getName();
        //
        Region cityRegion = regionMapper.selectByPrimaryKey(areaRegion.getPid());
        String city = cityRegion.getName();
        //
        Region provinceRegion = regionMapper.selectByPrimaryKey(cityRegion.getPid());
        String province = provinceRegion.getName();
        //完整的地址为
        String wholeAddress = province + " " + city + " " + area + " " + address.getAddress();
        //优惠价格
        BigDecimal couponPrice = new BigDecimal(0);
        if(couponId == 0){

        }else {
             couponPrice = couponMapper.selectDiscountById(couponId);
        }
        //优惠积分减免，integral_price, 设置为0
        BigDecimal integralPrice = new BigDecimal(0);
        //团购优惠价减免，不要求做
        BigDecimal grouponPrice = new BigDecimal(0);

        //商品价格
        //Cart cart = cartMapper.selectByPrimaryKey(cartId);
        BigDecimal goodPrice = new BigDecimal(0);
        //邮费，默认为0,
        BigDecimal freightPrice = new BigDecimal(0);
        for (Cart cart : cartList) {
            //获得商品的总价格
            goodPrice = goodPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
        }
        //根据商品总价格goodPrice去判断邮费
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
        if(goodPrice.doubleValue() >= systemFreightMin){
            //邮费不变，还是0
        }else
            freightPrice = new BigDecimal(systemFreightValue);
        //订单费用
        BigDecimal orderPrice = (goodPrice.add(freightPrice)).subtract(couponPrice);
        //实付费用
        BigDecimal actualPrice = orderPrice.subtract(integralPrice);

        //新建order
        Order order = new Order(null, userId, orderSn, orderStatus, consignee, mobile, wholeAddress, message, goodPrice,
                freightPrice, couponPrice, integralPrice, grouponPrice, orderPrice, actualPrice, null, null, null, null,
                null, null, (short) 0, null, new Date(), new Date(), false);
        orderMapper.insertSelective(order);
        Integer orderId = order.getId();

        //向order_goods插入数据
        //这里要根据cartList中的个数来插入了
        for (Cart cart : cartList) {
            //设置对应的deleted为true
            cart.setDeleted(true);
            cartMapper.updateByPrimaryKeySelective(cart);

            //减少库存
            Short number = cart.getNumber();
            Integer productId = cart.getProductId();
            GoodsProduct goodsProduct = productMapper.selectByPrimaryKey(productId);
            goodsProduct.setNumber(goodsProduct.getNumber() - number );
            productMapper.updateByPrimaryKeySelective(goodsProduct);

            //goodsId
            Integer goodsId = cart.getGoodsId();
            //goodsName
            String goodsName = cart.getGoodsName();
            //productId,number 上面都有了
            //售价
            BigDecimal price = cart.getPrice();
            //Spec
            String[] specifications = cart.getSpecifications();
            //pic
            String picUrl = cart.getPicUrl();

            OrderGoods orderGoods = new OrderGoods(null, orderId, goodsId, goodsName, goodsId.toString(), productId, number, price,
                    specifications, picUrl, 0, new Date(), new Date(), false);
            orderGoodsMapper.insertSelective(orderGoods);
        }

        return new OrderSubmitVO(orderId);
    }

    private String createOrderSn(){
        //当前日期 + 六位随机数
        String str = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int r = (int) ((Math.random()*9 + 1) * 100000);
        return str + r;
    }
}

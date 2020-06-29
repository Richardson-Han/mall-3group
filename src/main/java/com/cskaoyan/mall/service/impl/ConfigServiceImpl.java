package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BO.ConfigExpressBO;
import com.cskaoyan.mall.bean.BO.ConfigMallBO;
import com.cskaoyan.mall.bean.BO.ConfigOrderBO;
import com.cskaoyan.mall.bean.BO.ConfigWXBO;
import com.cskaoyan.mall.bean.System;
import com.cskaoyan.mall.mapper.SystemMapper;
import com.cskaoyan.mall.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    SystemMapper systemMapper;

    /**
     * 查询system表中的商场配置相关的数据
     * @return
     */
    @Override
    public ConfigMallBO queryConfigMall() {
        String address = systemMapper.selectConfigInfo("cskaoyan_mall_mall_address");
        String name = systemMapper.selectConfigInfo("cskaoyan_mall_mall_name");
        String phone = systemMapper.selectConfigInfo("cskaoyan_mall_mall_phone");
        String qq = systemMapper.selectConfigInfo("cskaoyan_mall_mall_qq");
        ConfigMallBO configMallBO = new ConfigMallBO(address,name,phone,qq);
        return configMallBO;
    }

    /**
     * 更新商场配置相关数据
     * @param configMallBO
     */
    @Override
    public void updateConfigMall(ConfigMallBO configMallBO) {
        Date date = new Date();
        systemMapper.updateMallConfig("cskaoyan_mall_mall_address",configMallBO.getCskaoyan_mall_mall_address(),date);
        systemMapper.updateMallConfig( "cskaoyan_mall_mall_phone",configMallBO.getCskaoyan_mall_mall_phone(),date);
        systemMapper.updateMallConfig("cskaoyan_mall_mall_qq",configMallBO.getCskaoyan_mall_mall_qq(),date);
        systemMapper.updateMallConfig("cskaoyan_mall_mall_name",configMallBO.getCskaoyan_mall_mall_name(),date);
    }

    /**
     * 查询运费配置相关数据
     * @return
     */
    @Override
    public ConfigExpressBO queryConfigExpress() {
        String value = systemMapper.selectConfigInfo("cskaoyan_mall_express_freight_value");
        String min = systemMapper.selectConfigInfo("cskaoyan_mall_express_freight_min");
        System system = new System();
        system.setKeyValue(value);
        return new ConfigExpressBO(value,min);
    }

    /**
     * 更新运费设置
     * @param configExpressBO
     */
    @Override
    public void updateConfigExpress(ConfigExpressBO configExpressBO) {
        Date date = new Date();
        systemMapper.updateMallConfig("cskaoyan_mall_express_freight_value",configExpressBO.getCskaoyan_mall_express_freight_value(),date);

        systemMapper.updateMallConfig("cskaoyan_mall_express_freight_min",configExpressBO.getCskaoyan_mall_express_freight_min(),date);
    }

    /**
     * 查询订单配置相关数据
     * @return
     */
    @Override
    public ConfigOrderBO queryConfigOrder() {
        String comment = systemMapper.selectConfigInfo("cskaoyan_mall_order_comment");
        String unconfirm = systemMapper.selectConfigInfo("cskaoyan_mall_order_unconfirm");
        String unpaid = systemMapper.selectConfigInfo("cskaoyan_mall_order_unpaid");
        return new ConfigOrderBO(comment,unconfirm,unpaid);
    }

    /**
     * 更改订单配置的相关数据
     * @param orderBO
     */
    @Override
    public void updateConfigOrder(ConfigOrderBO orderBO) {
        Date date = new Date();
        systemMapper.updateMallConfig("cskaoyan_mall_order_comment",orderBO.getCskaoyan_mall_order_comment(),date);
        systemMapper.updateMallConfig("cskaoyan_mall_order_unpaid",orderBO.getCskaoyan_mall_order_unpaid(),date);
        systemMapper.updateMallConfig("cskaoyan_mall_order_unconfirm",orderBO.getCskaoyan_mall_order_unconfirm(),date);
    }

    /**
     * 查看小程序配置相关数据
     * @return
     */
    @Override
    public ConfigWXBO queryConfigWX() {
        String goods = systemMapper.selectConfigInfo("cskaoyan_mall_wx_catlog_goods");
        String list = systemMapper.selectConfigInfo("cskaoyan_mall_wx_catlog_list");
        String brand = systemMapper.selectConfigInfo("cskaoyan_mall_wx_index_brand");
        String index_hot = systemMapper.selectConfigInfo("cskaoyan_mall_wx_index_hot");
        String index_new = systemMapper.selectConfigInfo("cskaoyan_mall_wx_index_new");
        String topic = systemMapper.selectConfigInfo("cskaoyan_mall_wx_index_topic");
        String share = systemMapper.selectConfigInfo("cskaoyan_mall_wx_share");

        return new ConfigWXBO(goods,list,brand,index_hot,index_new,topic,share);
    }

    /**
     * 更改小程序相关配置
     * @param configWXBO
     */
    @Override
    public void updateConfigWX(ConfigWXBO configWXBO) {
        Date date = new Date();
        systemMapper.updateMallConfig("cskaoyan_mall_wx_catlog_goods",configWXBO.getCskaoyan_mall_wx_catlog_goods(),date);
        systemMapper.updateMallConfig("cskaoyan_mall_wx_catlog_list",configWXBO.getCskaoyan_mall_wx_catlog_list(),date);
        systemMapper.updateMallConfig("cskaoyan_mall_wx_index_brand",configWXBO.getCskaoyan_mall_wx_index_brand(),date);
        systemMapper.updateMallConfig("cskaoyan_mall_wx_index_hot",configWXBO.getCskaoyan_mall_wx_index_hot(),date);
        systemMapper.updateMallConfig("cskaoyan_mall_wx_index_new",configWXBO.getCskaoyan_mall_wx_index_new(),date);
        systemMapper.updateMallConfig("cskaoyan_mall_wx_indexd_topic",configWXBO.getCskaoyan_mall_wx_index_topic(),date);
        systemMapper.updateMallConfig("cskaoyan_mall_wx_share",configWXBO.getCskaoyan_mall_wx_share(),date);
    }
}

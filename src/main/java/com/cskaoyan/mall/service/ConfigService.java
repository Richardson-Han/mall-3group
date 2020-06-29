package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BO.ConfigExpressBO;
import com.cskaoyan.mall.bean.BO.ConfigMallBO;
import com.cskaoyan.mall.bean.BO.ConfigOrderBO;
import com.cskaoyan.mall.bean.BO.ConfigWXBO;

public interface ConfigService {
    ConfigMallBO queryConfigMall();

    void updateConfigMall(ConfigMallBO configMallBO);

    ConfigExpressBO queryConfigExpress();

    void updateConfigExpress(ConfigExpressBO configExpressBO);

    ConfigOrderBO queryConfigOrder();

    void updateConfigOrder(ConfigOrderBO orderBO);

    ConfigWXBO queryConfigWX();

    void updateConfigWX(ConfigWXBO configWXBO);
}

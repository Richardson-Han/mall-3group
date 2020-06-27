package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.BaseRespVo;
import com.cskaoyan.mall.bean.GroupOnRules;

import java.util.Map;

public interface GroupService {
    BaseData queryGroupOnRules(Integer page, Integer limit, String sort, String desc);

    Boolean update(GroupOnRules groupOnRules);

    BaseRespVo create(GroupOnRules groupOnRules);

    Integer delete(GroupOnRules groupOnRules);
}

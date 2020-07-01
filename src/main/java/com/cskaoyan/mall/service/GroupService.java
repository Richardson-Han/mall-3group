package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.GroupOnRules;

public interface GroupService {
    BaseData queryGroupOnRules(Integer page, Integer limit, String sort, String desc, Integer goodsId);

    Boolean update(GroupOnRules groupOnRules);

    BaseRespVo create(GroupOnRules groupOnRules);

    Integer delete(GroupOnRules groupOnRules);

    BaseData listRecord(Integer page, Integer limit, String sort, String order, Integer goodsId);

    Boolean isGroupIn(Integer id);
}

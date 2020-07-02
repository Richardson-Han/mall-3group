package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.wx.BO.CollectListBO;
import com.cskaoyan.mall.bean.wx.VO.CollectAddOrDeleteVO;
import com.cskaoyan.mall.bean.wx.VO.CollectListVO;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface CollectService {
    BaseData queryCollect(Integer page, Integer limit, String sort, String order, Integer userId, Integer valueId);

    CollectListVO queryWXCollectList(CollectListBO collectListBO, String username);

    CollectAddOrDeleteVO addordelete(Map map, String username);
}

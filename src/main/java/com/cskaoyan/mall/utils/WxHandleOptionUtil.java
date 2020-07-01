package com.cskaoyan.mall.utils;

import com.cskaoyan.mall.bean.wx.HandleOption;

public class WxHandleOptionUtil {

    public static HandleOption getHandleOption(Short statusNum){
        HandleOption handleOption = new HandleOption();
        switch (statusNum){
            //全部
            case 0 :
                handleOption.setDelete(true);
                break;
            //待付款
            case 1 :
                handleOption.setPay(true);
                break;
            //待发货
            case 2 :
                break;
            //待收货
            case 3 :
                handleOption.setConfirm(true);
                break;
            //待评价
            case 4 :
                handleOption.setComment(true);
                handleOption.setDelete(true);
                handleOption.setRebuy(true);
                break;
        }
        return handleOption;
    }
}

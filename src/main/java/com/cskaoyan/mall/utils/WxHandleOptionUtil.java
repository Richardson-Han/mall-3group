package com.cskaoyan.mall.utils;

import com.cskaoyan.mall.bean.wx.HandleOption;

public class WxHandleOptionUtil {

    public static HandleOption getHandleOption(Short statusNum) {
        HandleOption handleOption = new HandleOption();
        switch (statusNum) {
            //待付款
            case 101:
                handleOption.setCancel(true);
                handleOption.setPay(true);
                break;
            //用户取消
            case 102:
                handleOption.setRebuy(true);
                break;
            //系统取消
            case 103:
                handleOption.setRebuy(true);
                break;
            //已付款
            case 201:
                handleOption.setRefund(true);
                break;
            //申请退款
            case 202:
                handleOption.setRebuy(true);
                handleOption.setCancel(true);
                break;
            //已退款
            case 203:
                handleOption.setRebuy(true);
                handleOption.setDelete(true);
                handleOption.setComment(true);
                break;
            //已发货
            case 301:
                handleOption.setRebuy(true);
                handleOption.setConfirm(true);
                handleOption.setRefund(true);
                break;
            //已收货
            case 401:
                handleOption.setRebuy(true);
                handleOption.setComment(true);
                handleOption.setDelete(true);
                break;
            //系统收货
            case 402:
                handleOption.setRebuy(true);
                handleOption.setComment(true);
                handleOption.setDelete(true);
                break;
        }
        return handleOption;
    }
}

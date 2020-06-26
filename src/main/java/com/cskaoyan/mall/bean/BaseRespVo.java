package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class BaseRespVo<T> {
    T data;
    String errmsg;
    Integer errno;

    public static BaseRespVo ok(){
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }
    public static BaseRespVo ok(Object data){
        BaseRespVo baseRespVo = ok();
        baseRespVo.setData(data);
        return baseRespVo;
    }
}

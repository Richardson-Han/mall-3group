package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class BaseRespVo<T> {
    T data;
    String errmsg;
    Integer errno;

    public BaseRespVo() {
    }

    public BaseRespVo(String errmsg, Integer errno) {
        this.errmsg = errmsg;
        this.errno = errno;
    }

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
    public static BaseRespVo err(String errmsg , Integer errno){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.errmsg=errmsg;
        baseRespVo.errno=errno;
        return baseRespVo;
    }
}

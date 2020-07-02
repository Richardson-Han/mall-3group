package com.cskaoyan.mall.bean.VO.wx;

import lombok.Data;

@Data
public class WXBaseRespVo<T> {
    T data;
    String errmsg;
    Integer errno;

    public WXBaseRespVo() {
    }

    public WXBaseRespVo(String errmsg, Integer errno) {
        this.errmsg = errmsg;
        this.errno = errno;
    }

    public static WXBaseRespVo ok(){
        WXBaseRespVo baseRespVo = new WXBaseRespVo ();
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        return baseRespVo;
    }
    public static WXBaseRespVo ok(Object data){
        WXBaseRespVo baseRespVo = ok();
        baseRespVo.setData(data);
        return baseRespVo;
    }

    public static WXBaseRespVo error() {
        WXBaseRespVo baseRespVo = new WXBaseRespVo ();
        baseRespVo.setErrmsg("失败");
        baseRespVo.setErrno(888);
        return baseRespVo;
    }

    public static WXBaseRespVo error(Object data) {
        WXBaseRespVo baseRespVo = error();
        baseRespVo.setData(data);
        return baseRespVo;
    }

    public static WXBaseRespVo error(String errmsg, Integer errno) {
        WXBaseRespVo baseRespVo = new WXBaseRespVo ();
        baseRespVo.setErrno(errno);
        baseRespVo.setErrmsg(errmsg);
        return baseRespVo;
    }

    public static WXBaseRespVo errorString(String errmsg) {
        WXBaseRespVo baseRespVo = new WXBaseRespVo ();
        baseRespVo.setErrno(444);
        baseRespVo.setErrmsg(errmsg);
        return baseRespVo;
    }

}

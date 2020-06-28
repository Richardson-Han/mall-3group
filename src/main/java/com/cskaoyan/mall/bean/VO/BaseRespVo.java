package com.cskaoyan.mall.bean.VO;

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

    public static BaseRespVo error() {
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrmsg("失败");
        baseRespVo.setErrno(888);
        return baseRespVo;
    }

    public static BaseRespVo error(Object data) {
        BaseRespVo baseRespVo = error();
        baseRespVo.setData(data);
        return baseRespVo;
    }

    public static BaseRespVo error(String errmsg,Integer errno) {
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(errno);
        baseRespVo.setErrmsg(errmsg);
        return baseRespVo;
    }

}

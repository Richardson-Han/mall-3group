package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class GoodsCommentListBO {
    Integer page;
    Integer limit;
    //为了两个构造方法，将这里写成String
    String userId;
    Integer valueId;
    String sort;
    String order;

    public GoodsCommentListBO() {
    }

    //下面两个构造方法多余， 懒得改了， 上面的userId可以写Integer，只用全部参数的构造方法
    public GoodsCommentListBO(Integer page, Integer limit, String sort, String order) {
        this.page = page;
        this.limit = limit;
        this.sort = sort;
        this.order = order;
    }

    public GoodsCommentListBO(Integer page, Integer limit, String userId, String sort, String order) {
        this.page = page;
        this.limit = limit;
        this.userId = userId;
        this.sort = sort;
        this.order = order;
    }

    public GoodsCommentListBO(Integer page, Integer limit, Integer valueId, String sort, String order) {
        this.page = page;
        this.limit = limit;
        this.valueId = valueId;
        this.sort = sort;
        this.order = order;
    }



    public GoodsCommentListBO(Integer page, Integer limit, String userId, Integer valueId, String sort, String order) {
        this.page = page;
        this.limit = limit;
        this.userId = userId;
        this.valueId = valueId;
        this.sort = sort;
        this.order = order;
    }
}

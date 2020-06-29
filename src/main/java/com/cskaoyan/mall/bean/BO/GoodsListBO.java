package com.cskaoyan.mall.bean.BO;

import lombok.Data;

@Data
public class GoodsListBO {
    Integer page;
    Integer limit;
    Integer goodsSn;
    String name;
    String sort;
    String order;

    public GoodsListBO() {
    }

    //下面两个构造方法多余， 懒得改了
    public GoodsListBO(Integer page, Integer limit, String sort, String order) {
        this.page = page;
        this.limit = limit;
        this.sort = sort;
        this.order = order;
    }

    public GoodsListBO(Integer page, Integer limit, Integer goodsSn, String sort, String order) {
        this.page = page;
        this.limit = limit;
        this.goodsSn = goodsSn;
        this.sort = sort;
        this.order = order;
    }

    public GoodsListBO(Integer page, Integer limit, String name, String sort, String order) {
        this.page = page;
        this.limit = limit;
        this.name = name;
        this.sort = sort;
        this.order = order;
    }

    public GoodsListBO(Integer page, Integer limit, Integer goodsSn, String name, String sort, String order) {
        this.page = page;
        this.limit = limit;
        this.goodsSn = goodsSn;
        this.name = name;
        this.sort = sort;
        this.order = order;
    }
}

package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class KeyWordBO {
    Integer page;
    Integer limit;
    String keyword;
    String url;
    String sort;
    String order;

    public KeyWordBO() {
    }

    public KeyWordBO(Integer page, Integer limit, String keyword, String url, String sort, String order) {
        this.page = page;
        this.limit = limit;
        this.keyword = keyword;
        this.url = url;
        this.sort = sort;
        this.order = order;
    }
}

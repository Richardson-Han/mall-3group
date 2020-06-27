package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Storage;

public interface StorageService {
    BaseData selectAllStorage(Integer page, Integer limit, String sort, String order);

    Storage picUpload(Storage storage);
}

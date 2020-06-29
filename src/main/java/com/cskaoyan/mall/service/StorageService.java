package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Storage;

public interface StorageService {
    BaseData selectAllStorage(Integer page, Integer limit,String key,String name, String sort, String order);

    Storage picUpload(Storage storage);

    Integer deleteStorage(Storage storage);

    Storage updateStorage(Storage storage);
}

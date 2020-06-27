package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Storage;
import com.cskaoyan.mall.bean.StorageExample;
import com.cskaoyan.mall.mapper.StorageMapper;
import com.cskaoyan.mall.service.StorageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    StorageMapper storageMapper;

    @Override
    public BaseData selectAllStorage(Integer page, Integer limit, String sort, String order) {
        StorageExample storageExample = new StorageExample();
        storageExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page,limit);
        List<Storage> storages = storageMapper.selectByExample(storageExample);
        PageInfo<Storage> pageInfo = new PageInfo<>(storages);
        long total = pageInfo.getTotal();
        return new BaseData(storages,total);
    }

    @Override
    public Storage picUpload(Storage storage) {
        storageMapper.insert(storage);
        storage.setId(storageMapper.getLastInsertId());
        return storage;
    }
}

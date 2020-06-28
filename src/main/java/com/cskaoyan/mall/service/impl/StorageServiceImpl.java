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

import java.util.Date;
import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    StorageMapper storageMapper;

    //挑选符合条件的storage对象
    @Override
    public BaseData selectAllStorage(Integer page, Integer limit,String key,String name, String sort, String order) {
        StorageExample storageExample = new StorageExample();
        storageExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page,limit);
        if(key != null){
            storageExample.createCriteria().andKeyEqualTo(key);
        }
        if(name != null){
            storageExample.createCriteria().andNameLike("%" + name + "%");
        }
        List<Storage> storages = storageMapper.selectByExample(storageExample);
        PageInfo<Storage> pageInfo = new PageInfo<>(storages);
        long total = pageInfo.getTotal();
        return new BaseData(storages,total);
    }

    //上传图片
    @Override
    public Storage picUpload(Storage storage) {
        storageMapper.insert(storage);
        storage.setId(storageMapper.getLastInsertId());
        return storage;
    }

    @Override
    public Integer deleteStorage(Storage storage) {
        return storageMapper.deleteByPrimaryKey(storage.getId());
    }

    //更新名字
    @Override
    public Storage updateStorage(Storage storage) {
        storage.setUpdateTime(new Date());
        Integer updateResult = storageMapper.updateByPrimaryKey(storage);
        if (updateResult > 0){
            return storage;
        }else {
            return null;
        }
    }
}

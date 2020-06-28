package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Log;
import com.cskaoyan.mall.bean.LogExample;
import com.cskaoyan.mall.mapper.LogMapper;
import com.cskaoyan.mall.service.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper logMapper;

    @Override
    public BaseData getLogList(Integer page, Integer limit, String name, String sort, String order) {
        LogExample logExample = new LogExample();
        logExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page,limit);
        if ( name != null){
            logExample.createCriteria().andAdminLike("%" + name + "%");
        }
        List<Log> logs = logMapper.selectByExample(logExample);
        PageInfo<Log> pageInfo = new PageInfo<>(logs);
        long total = pageInfo.getTotal();
        return new BaseData(logs,total);
    }
}

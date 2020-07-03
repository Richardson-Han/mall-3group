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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper logMapper;

    @Override
    public BaseData getLogList(Integer page, Integer limit, String name, String sort, String order) {
        LogExample logExample = new LogExample();
        logExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        if (name != null) {
            logExample.createCriteria().andAdminLike("%" + name + "%");
        }
        List<Log> logs = logMapper.selectByExample(logExample);
        PageInfo<Log> pageInfo = new PageInfo<>(logs);
        long total = pageInfo.getTotal();
        return new BaseData(logs, total);
    }

    @Override
    public void setLogin(String username) {
        Date date = new Date();
        Log log = new Log();
        try {
            String address = InetAddress.getLocalHost().getHostAddress().toString();
            log.setIp(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        log.setAdmin(username);
        log.setUpdateTime(date);
        log.setAddTime(date);
        log.setAction("登入");
        log.setStatus(true);
        log.setType(1);
        logMapper.insertSelective(log);
    }

    @Override
    public void setLogout(String username) {
        Date date = new Date();
        Log log = new Log();
        try {
            String address = InetAddress.getLocalHost().getHostAddress().toString();
            log.setIp(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        log.setAdmin(username);
        log.setUpdateTime(date);
        log.setAddTime(date);
        log.setAction("登出");
        log.setStatus(true);
        log.setType(1);
        logMapper.insertSelective(log);
    }

    @Override
    public void setAdminCreate(String username, String operationName, Integer id) {
        Date date = new Date();
        Log log = new Log();
        try {
            String address = InetAddress.getLocalHost().getHostAddress().toString();
            log.setIp(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        log.setAdmin(username);
        log.setUpdateTime(date);
        log.setAddTime(date);
        log.setAction("管理员创建" + operationName);
        log.setStatus(true);
        log.setResult("创建成功,id为:" + id);
        log.setType(1);
        logMapper.insertSelective(log);
    }

    @Override
    public void updateAdmin(String username, String updateName, String operationName) {
        Date date = new Date();
        Log log = new Log();
        try {
            String address = InetAddress.getLocalHost().getHostAddress().toString();
            log.setIp(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        log.setAdmin(username);
        log.setUpdateTime(date);
        log.setAddTime(date);
        log.setAction("管理员修改" + operationName);
        log.setStatus(true);
        log.setType(1);
        log.setResult(updateName);
        logMapper.insertSelective(log);
    }

    @Override
    public void deleteAdmin(String username,Integer id) {
        Date date = new Date();
        Log log = new Log();
        try {
            String address = InetAddress.getLocalHost().getHostAddress().toString();
            log.setIp(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        log.setAdmin(username);
        log.setUpdateTime(date);
        log.setAddTime(date);
        log.setAction("管理员修改deleted(虚拟删除)");
        log.setStatus(true);
        log.setType(1);
        log.setResult("删除成功");
        logMapper.insertSelective(log);
    }
}

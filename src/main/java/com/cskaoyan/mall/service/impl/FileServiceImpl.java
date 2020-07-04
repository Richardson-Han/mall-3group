package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.config.AliyunComponent;
import com.cskaoyan.mall.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author 韩
 * @create 2020-07-05 0:38
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    AliyunComponent aliyunComponent;

    @Override
    public void fileUpload(String fileName, File file) {
        aliyunComponent.fileUpload(fileName,file);
    }

    @Override
    public void sendMsg(String phone, String code) {
        aliyunComponent.sendMsg(phone,code);
    }
}

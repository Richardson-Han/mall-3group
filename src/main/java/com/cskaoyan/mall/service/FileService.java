package com.cskaoyan.mall.service;

import java.io.File;

/**
 * @author 韩
 * @create 2020-07-05 0:37
 */
public interface FileService {
    void fileUpload(String fileName, File file);

    void sendMsg(String phone,String code);
}

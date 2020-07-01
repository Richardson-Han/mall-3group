package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.Storage;
import com.cskaoyan.mall.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author 韩
 * @create 2020-06-30 2:33
 */
@RestController
@RequestMapping("/wx/storage")
public class WXStorageContorller {

    @Autowired
    StorageService storageService;


    /**
     * 文件上传
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void picUpload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request,
                                        HttpServletResponse response) throws IOException {
        HttpServletResponse servResponse = response;
        //设置响应属性
        servResponse.setContentType("application/json;charset=UTF-8");
        //文件没取到 返回404
        if(multipartFile.isEmpty()){
            servResponse.setStatus(404);
        }
        Storage storage = new Storage();
        //文件名
        String filename = multipartFile.getOriginalFilename();
        //后缀名
        String endName = filename.substring(filename.lastIndexOf("."));
        //文件存储地址
        String filePath = "D:\\fileUpload\\";
        //新文件名
        String newfilename = UUID.randomUUID() + endName;
        //保存文件
        File downloadFile = new File(filePath + newfilename);
        if (!downloadFile.getParentFile().exists()){
            downloadFile.getParentFile().mkdirs();
        }
        multipartFile.transferTo(downloadFile);
        //获取文件的大小
        Integer fileSize = (int)downloadFile.length();
        //设置返回值并存入数据库
        storage.setKey(newfilename);
        storage.setName(filename);
        endName = endName.replace(".","");
        storage.setType("image/" + endName);
        storage.setSize(fileSize);
        Date date = new Date();
        storage.setAddTime(date);
        storage.setUpdateTime(date);
        storage.setUrl("http://localhost:8081/pic/" + newfilename);

        storage = storageService.picUpload(storage);
        //设置响应值
        servResponse.setStatus(200);
        servResponse.encodeURL(storage.getUrl());
        String method = request.getMethod();
        servResponse.setHeader("Vary","歪~");
    }
}

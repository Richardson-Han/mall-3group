package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.BaseRespVo;
import com.cskaoyan.mall.bean.Storage;
import com.cskaoyan.mall.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("admin/storage")
public class StorageController {

    @Autowired
    StorageService storageService;

    //获取storage对应的list
    @RequestMapping("list")
    public BaseRespVo selectAllStorage(Integer page,Integer limit,String sort,String order){
        BaseData baseData = storageService.selectAllStorage(page,limit,sort,order);
        return BaseRespVo.ok(baseData);
    }

    //图片上传
    @PostMapping("create")
    public BaseRespVo picUpload(@RequestParam("file")MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
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
        storage.setType(endName);
        storage.setSize(fileSize);
        storage.setAddTime(new Date());
        storage.setUrl("http://localhost:8080/pic/" + newfilename);

        storage = storageService.picUpload(storage);
        return BaseRespVo.ok(storage);
    }

}

package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.Storage;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.StorageService;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author 韩
 * @create 2020-06-30 2:33
 */
@Controller
@RequestMapping("/wx/storage")
public class WXStorageContorller {

    @Autowired
    StorageService storageService;


    /**
     * 还未开始
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @RequiresGuest
    @RequestMapping(value = "/upload")
    public BaseRespVo picUpload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        // if(multipartFile.isEmpty()){
        //     return BaseRespVo.error();
        // }
        // Storage storage = new Storage();
        // //文件名
        // String filename = multipartFile.getOriginalFilename();
        // //后缀名
        // String endName = filename.substring(filename.lastIndexOf("."));
        // //文件存储地址
        // String filePath = "D:\\fileUpload\\";
        // //新文件名
        // String newfilename = UUID.randomUUID() + endName;
        // //保存文件
        // File downloadFile = new File(filePath + newfilename);
        // if (!downloadFile.getParentFile().exists()){
        //     downloadFile.getParentFile().mkdirs();
        // }
        // multipartFile.transferTo(downloadFile);
        // //获取文件的大小
        // Integer fileSize = (int)downloadFile.length();
        // //设置返回值并存入数据库
        // storage.setKey(newfilename);
        // storage.setName(filename);
        // endName = endName.replace(".","");
        // storage.setType("image/" + endName);
        // storage.setSize(fileSize);
        // storage.setAddTime(new Date());
        // storage.setUpdateTime(new Date());
        // storage.setUrl("http://localhost:8081/pic/" + newfilename);
        //
        // storage = storageService.picUpload(storage);
        // return BaseRespVo.ok(storage);
        return BaseRespVo.ok();
    }
}

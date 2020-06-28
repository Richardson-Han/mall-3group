package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.KeyWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/keyword")
public class KeyWordController {

    @Autowired
    KeyWordService keyWordService;

    /**
     *  显示 和 查询 keyword
     */
    @RequestMapping("list")
    public BaseRespVo list(KeyWordBO keyWordBO){
        BaseData baseData = keyWordService.queryKeyWord(keyWordBO);
        return BaseRespVo.ok(baseData);
    }

    /**
     *  增加 keyword
     */
    @RequestMapping("create")
    public BaseRespVo create(@RequestBody KeyWord keyWord){
        KeyWordInsertVO keyWordInsertVO = keyWordService.createKeyWord(keyWord);
        if(keyWordInsertVO == null){
            return BaseRespVo.errorString("该keyword已存在");
        }
        return BaseRespVo.ok(keyWordInsertVO);
    }

    /**
     *  编辑 keyword
     */
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody KeyWord keyWord){
        KeyWordInsertVO keyWordInsertVO = keyWordService.updateKeyWord(keyWord);
        if(keyWordInsertVO == null){
            return BaseRespVo.errorString("该keyword已存在");
        }
        return BaseRespVo.ok(keyWordInsertVO);
    }

    /**
     *  虚拟删除 keyword
     */
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody KeyWord keyWord){
         keyWordService.deleteKeyWord(keyWord);
        return BaseRespVo.ok();
    }

}

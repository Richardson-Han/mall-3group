package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.VO.wx.CatalogCurrentVO;
import com.cskaoyan.mall.bean.VO.wx.CatalogVO;
import com.cskaoyan.mall.service.CatalogService;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 杨
 * @create 2020-07-02 11:37
 */

@RestController
@RequestMapping("wx/catalog")
public class WXCatalogController {

    @Autowired
    CatalogService catalogService;


    @RequestMapping("index")
    public BaseRespVo catalogIndex(){
        CatalogVO catalog = catalogService.catalogIndex();
        if ( catalog != null ){
            return BaseRespVo.ok(catalog);
        }else {
            return BaseRespVo.error();
        }
    }

    @RequiresGuest
    @RequestMapping("current")
    public BaseRespVo catalogCurrent(Integer id){
        CatalogCurrentVO catalogCurrent = catalogService.catalogCurrent(id);
        if ( catalogCurrent != null ){
            return BaseRespVo.ok(catalogCurrent);
        }else {
            return BaseRespVo.error();
        }
    }
}

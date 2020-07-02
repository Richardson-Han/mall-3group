package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.wx.VO.BrandDetailVO;
import com.cskaoyan.mall.bean.wx.WXBrandListData;
import com.cskaoyan.mall.service.BrandService;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杨
 * @create 2020-07-01 22:47
 */


@RestController
@RequestMapping("wx/brand")
public class WXBrandController {

    @Autowired
    BrandService brandService;

    @RequiresGuest
    @RequestMapping("list")
    public BaseRespVo getBrandList(Integer page, Integer size) {
        WXBrandListData baseData = brandService.getBrandList(page, size);
        return BaseRespVo.ok(baseData);
    }

    @RequiresGuest
    @RequestMapping("detail")
    public BaseRespVo getBrandDetail(Integer id) {
        Brand brand = brandService.getBrandDetail(id);
        BrandDetailVO brandDetailVO = new BrandDetailVO(brand);
        if ( brand != null ){
            return BaseRespVo.ok(brandDetailVO);
        }else {
            return BaseRespVo.error();
        }
    }
}

package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.bean.BrandAddVo;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/* *
@author  Walker-胡
@create 2020-06-28 14:43
*/
@RestController
@RequestMapping("/admin/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @GetMapping("/list")
    public BaseRespVo brandPageList(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit,
            @RequestParam(value = "id",required = false) Integer id,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam("sort") String sort,
            @RequestParam("order") String order)
    {
        Map<String, Object> map = brandService.queryBrandPageList (page, limit, id, name, sort, order);

        return BaseRespVo.ok (map);
    }

    @PostMapping("/create")
    public BaseRespVo addBrand(@RequestBody BrandAddVo brandAddVo){
        Brand brand=brandService.add(brandAddVo);
        return BaseRespVo.ok (brand);
    }
    @PostMapping("/delete")
    public BaseRespVo deleteBrand(@RequestBody Brand brand){
          boolean flag=brandService.delete(brand);
          if(flag) return BaseRespVo.ok ();
          return BaseRespVo.error ("删除失败",605);

    }
    @PostMapping("/update")
    public BaseRespVo updateBrand(@RequestBody Brand brand){
        boolean flag=brandService.updateByBrand(brand);
        if(flag) return BaseRespVo.ok ();
        return BaseRespVo.error ("更新失败",604);
    }
}

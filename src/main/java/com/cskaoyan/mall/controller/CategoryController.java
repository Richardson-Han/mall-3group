package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseRespVo;
import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.bean.CategoryVo;
import com.cskaoyan.mall.service.CategoryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.channels.Pipe;
import java.util.List;


/* *
@author  Walker-胡
@create 2020-06-28 20:41
*
* 显示商品目录
* 其中有一级类目L1和二级类目L2
*/
@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    //查询一级类目
    @RequestMapping("/l1")
    public BaseRespVo getL1Category(){
        List<CategoryVo>  lists=categoryService.queryL1();
        return BaseRespVo.ok (lists);
    }
    //查询所有类目
    @RequestMapping("/list")
    public BaseRespVo getListCategory(){
        List<Category> listCategory=categoryService.queryList();
        return BaseRespVo.ok (listCategory);
    }
   //添加商品类目
    @PostMapping("/create")
    public BaseRespVo addCategory(@RequestBody Category category){
        int i = categoryService.addCategory (category);

        if (i==1){
            return BaseRespVo.ok ();
        }if(i==2){ return BaseRespVo.error ("类目名称不能重复添加",605);}
        return BaseRespVo.error ("添加失败",604);
    }
    //更新商品目录
    @PostMapping("/update")
    public BaseRespVo updateCategory(@RequestBody Category category){
        int i = categoryService.updateCategory (category);
        if(i==2){
           return BaseRespVo.error ("同级目录商品名称重复",605);
       }
        if(i==1){return BaseRespVo.ok ();}
        return BaseRespVo.error ("更新失败",604);
    }
    //删除商品目录
    @PostMapping("/delete")
    public BaseRespVo deleteCategory(@RequestBody Category category){
        if(categoryService.deleteCategory(category)){

        }
        return BaseRespVo.ok();
    }
}

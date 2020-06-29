package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.BO.GoodsListBO;
import com.cskaoyan.mall.bean.BO.GoodsUpdateBO;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.VO.GoodsCatAndBrandVO;
import com.cskaoyan.mall.bean.VO.GoodsDetailVO;
import com.cskaoyan.mall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;


    /**
     *  商品列表
     *  查询商品
     */
    @RequestMapping("list")
    public BaseRespVo list(GoodsListBO goodsListBO){
        BaseData baseData = goodsService.queryGoods(goodsListBO);
        return BaseRespVo.ok(baseData);
    }

    /**
     *  编辑商品
     *  先获取商品信息
     */
    @RequestMapping("detail")
    public BaseRespVo detail(Integer id){
        GoodsDetailVO list = goodsService.queryGoods(id);
        return BaseRespVo.ok(list);
    }

    /**
     *  编辑商品
     *  再获取商品类别及产商
     */
    @RequestMapping("catAndBrand")
    public BaseRespVo catAndBrand(){
        GoodsCatAndBrandVO data = goodsService.queryCatAndBrand();
        return BaseRespVo.ok(data);
    }

    /**
     *  修改商品信息
     */
    @RequestMapping("update")
    public BaseRespVo goodsUpdate(@RequestBody GoodsUpdateBO goodsUpdateBO){
        int i = goodsService.updateGoods(goodsUpdateBO);
        if(i == 0){
            //表示当前操作失败，要有错误提示，暂时先没写
            return BaseRespVo.errorString("该商品编号已存在");
        }
        return BaseRespVo.ok();
    }

    /**
     *  新增商品信息
     */
    @RequestMapping("create")
    public BaseRespVo goodsCreate(@RequestBody GoodsUpdateBO goodsUpdateBO){
        int i = goodsService.createGoods(goodsUpdateBO);
        if(i == 0){
            return BaseRespVo.errorString("该商品编号已存在");
        }
        return BaseRespVo.ok();
    }

    /**
     *  删除商品信息
     *  虚拟删除，只是将deleted的值改变
     */
    @RequestMapping("delete")
    public BaseRespVo goodsDelete(@RequestBody Goods goods){
        goodsService.goodsDelete(goods);
        return BaseRespVo.ok();
    }




}

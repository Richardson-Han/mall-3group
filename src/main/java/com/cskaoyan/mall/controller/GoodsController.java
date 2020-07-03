package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.BO.GoodsListBO;
import com.cskaoyan.mall.bean.BO.GoodsUpdateBO;
import com.cskaoyan.mall.bean.VO.GoodsCatAndBrandVO;
import com.cskaoyan.mall.bean.VO.GoodsDetailVO;
import com.cskaoyan.mall.service.GoodsService;
import com.cskaoyan.mall.service.LogService;
import com.cskaoyan.mall.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Select;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;
    @Autowired
    LogService logService;
    @Autowired
    UserService userService;

    String operation = "商品";


    /**
     * 商品列表
     * 查询商品
     */
    @RequestMapping("list")
    public BaseRespVo list(GoodsListBO goodsListBO) {
        BaseData baseData = goodsService.queryGoods(goodsListBO);
        return BaseRespVo.ok(baseData);
    }

    /**
     * 编辑商品
     * 先获取商品信息
     */
    @RequestMapping("detail")
    public BaseRespVo detail(Integer id) {
        GoodsDetailVO list = goodsService.queryGoods(id);
        String username = (String) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (username != null){
            Integer userId = userService.wxselectIdByUsername(username);
            goodsService.insertfootprintByUseridAndGoodsid(userId,id);
        }
        return BaseRespVo.ok(list);
    }

    /**
     * 编辑商品
     * 再获取商品类别及产商
     */
    @RequestMapping("catAndBrand")
    public BaseRespVo catAndBrand() {
        GoodsCatAndBrandVO data = goodsService.queryCatAndBrand();
        return BaseRespVo.ok(data);
    }

    /**
     * 修改商品信息
     */
    @RequestMapping("update")
    public BaseRespVo goodsUpdate(@RequestBody GoodsUpdateBO goodsUpdateBO) {
        int i = goodsService.updateGoods(goodsUpdateBO);
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipals().getPrimaryPrincipal();
        if (i == 0) {
            //表示当前操作失败，要有错误提示，暂时先没写
            return BaseRespVo.errorString("该商品编号已存在");
        } else if (i == -1) {
            return BaseRespVo.errorString("请完整填写商品介绍的信息");
        }
        if (username != null) {
            logService.updateAdmin(username, goodsUpdateBO.getGoods().getName(), operation);
        }
        return BaseRespVo.ok();
    }

    /**
     * 新增商品信息
     */
    @RequestMapping("create")
    public BaseRespVo goodsCreate(@RequestBody GoodsUpdateBO goodsUpdateBO) {
        int i = goodsService.createGoods(goodsUpdateBO);
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipals().getPrimaryPrincipal();
        Goods goods = goodsUpdateBO.getGoods();
        goods.setId(goodsService.selectLastId());
        if (i == 0) {
            return BaseRespVo.errorString("该商品编号已存在");
        } else if (i == -1) {
            return BaseRespVo.errorString("请完整填写商品介绍的信息");
        }
        if (username != null) {
            logService.setAdminCreate(username, operation, goods.getId());
        }
        return BaseRespVo.ok();
    }

    /**
     * 删除商品信息
     * 虚拟删除，只是将deleted的值改变
     */
    @RequestMapping("delete")
    public BaseRespVo goodsDelete(@RequestBody Goods goods) {
        goodsService.goodsDelete(goods);
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipals().getPrimaryPrincipal();
        logService.deleteAdmin(username, goods.getId());
        return BaseRespVo.ok();
    }

}

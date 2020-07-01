package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/***
 * @author 社会主义好
 * @date 2020-06-30 11:53 星期二 
 *
 */
@RestController
@RequestMapping("wx/goods")
public class WXGoodsController {
    @Autowired
    GoodsService goodsService;

    @RequestMapping("category")
    public BaseRespVo category(Integer id) {
        Map data = goodsService.category(id);
        return BaseRespVo.ok(data);
    }

    @RequestMapping("list")
    public BaseRespVo list(Integer categoryId, Integer page, Integer size, String keyword, String sort, String order) {
        Map data = goodsService.list(categoryId, page, size, keyword, sort, order);
        return BaseRespVo.ok(data);
    }

    @RequestMapping("count")
    public BaseRespVo count() {
        Long goodsTotal = goodsService.getGoodsTotal();
        Map map = new HashMap();
        map.put("goodsCount", goodsTotal);
        return BaseRespVo.ok(map);
    }

    @RequestMapping("detail")
    public BaseRespVo detaile(Integer id) {
        Map map = goodsService.detail(id);
        return BaseRespVo.ok(map);
    }

    @RequestMapping("related")
    public BaseRespVo related(Integer id) {
        Map map = goodsService.related(id);
        return BaseRespVo.ok(map);
    }
}

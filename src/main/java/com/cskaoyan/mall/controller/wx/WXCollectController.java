package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.Collect;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.wx.BO.CollectListBO;
import com.cskaoyan.mall.bean.wx.VO.CartListVO;
import com.cskaoyan.mall.bean.wx.VO.CollectAddOrDeleteVO;
import com.cskaoyan.mall.bean.wx.VO.CollectListVO;
import com.cskaoyan.mall.service.CollectService;
import com.cskaoyan.mall.utils.WXTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Fang
 * @create 2020/7/2-12:35
 */

@RestController
@RequestMapping("wx/collect")
public class WXCollectController {
    @Autowired
    CollectService collectService;

    @RequestMapping("list")
    public BaseRespVo list(CollectListBO collectListBO, HttpServletRequest request){
        String username = WXTokenUtils.requestToUsername(request);
        CollectListVO collectListVO = collectService.queryWXCollectList(collectListBO, username);
        return BaseRespVo.ok(collectListVO);
    }

    @RequestMapping("addordelete")
    public BaseRespVo addordelete(@RequestBody Map map, HttpServletRequest request){
        String username = WXTokenUtils.requestToUsername(request);
        CollectAddOrDeleteVO addordelete = collectService.addordelete(map, username);
        return BaseRespVo.ok(addordelete);
    }

}

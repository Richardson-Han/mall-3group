package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.BaseRespVo;
import com.cskaoyan.mall.bean.GroupOnRules;
import com.cskaoyan.mall.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("admin/groupon")
public class GroupOnController {
    @Autowired
    GroupService groupService;

    /**
     * 查看团购规则
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit, String sort, String order){
        BaseData baseData = groupService.queryGroupOnRules(page,limit,sort,order);
        return BaseRespVo.ok(baseData);
    }

    /**
     * 编辑
     * @param groupOnRules
     * @return
     */
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody GroupOnRules groupOnRules){
        //先判断过期时间是否合法
        Date expireTime;
        Date updateTime;
        expireTime =  groupOnRules.getExpireTime();
        updateTime =groupOnRules.getUpdateTime();
        if(updateTime.after(expireTime)) {
            return new BaseRespVo("参数值不对", 402);
        }
        //更新不成功
        if(!groupService.update(groupOnRules)){
            return new BaseRespVo("参数值不对", 402);
        }
        return BaseRespVo.ok();
    }

    /**
     * 添加团购
     * @param groupOnRules
     * @return
     */
    @RequestMapping("create")
    public BaseRespVo create(@RequestBody GroupOnRules groupOnRules){
        BaseRespVo baseRespVo = groupService.create(groupOnRules);
        return baseRespVo;
    }

    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody GroupOnRules groupOnRules){
        groupOnRules.setDeleted(true);
        Integer result = groupService.delete(groupOnRules);
        if(result != 1){
            return BaseRespVo.err("出错了",402);
        }else {
            return BaseRespVo.ok();
        }
    }
}

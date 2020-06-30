package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.Issue;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.service.IssueService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/* *
@author  Walker-胡
@create 2020-06-29 17:16
*/
@RestController
@RequestMapping("/admin/issue")
public class IssueController {
    @Autowired
    IssueService issueService;

    @GetMapping("/list")
    public BaseRespVo getList(@RequestParam("page") Integer page,
                              @RequestParam ("limit") Integer limit,
                              @RequestParam("sort")  String sort,
                              @RequestParam ("order") String order,
                              @RequestParam(value = "question", required=false) String question){
        Map<String ,Object> map=issueService.queryIssueList(page,limit,sort,order,question);
        return BaseRespVo.ok (map);
    }
     //新增提问 ,若添加重复的问题和回答则报错
     @PostMapping("/create")
     public BaseRespVo addIssue(@RequestBody Issue issue){
      if( issueService.addIssue(issue)){
             return BaseRespVo.ok ( );
         }
        return  BaseRespVo.error ("添加失败",603);
    }

    //更新
    @PostMapping("/update")
    public BaseRespVo updateIssue(@RequestBody Issue issue){
        int i=issueService.updateIssue(issue);
        if(i==1){
            return BaseRespVo.ok ();
        }
        if(i==2){
            return  BaseRespVo.error ("不能提交相同内容",604);
        }
        return  BaseRespVo.error ("添加失败",603);
    }

    //删除
    @PostMapping("/delete")
    public BaseRespVo deleteIssue(@RequestBody Issue issue){

        if(issueService.deleteIssue(issue)){
            return BaseRespVo.ok ();
        }
        return  BaseRespVo.error ("删除失败",603);
    }
}


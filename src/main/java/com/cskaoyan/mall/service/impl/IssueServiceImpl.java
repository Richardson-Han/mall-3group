package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Issue;
import com.cskaoyan.mall.bean.IssueExample;
import com.cskaoyan.mall.mapper.IssueMapper;
import com.cskaoyan.mall.service.IssueService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* *
@author  Walker-胡
@create 2020-06-29 17:37
*/

@Service
public class IssueServiceImpl implements IssueService {
    @Autowired
    IssueMapper issueMapper;
    @Override
    public Map<String, Object> queryIssueList(Integer page, Integer limit, String sort, String order, String question) {
        Map <String,Object>map=new HashMap<> ();
        PageHelper.startPage (page,limit);
        List<Issue> issueList=issueMapper.queryIssuePageList(sort,order,question);
        PageInfo pageInfo = new PageInfo (issueList);
        map.put ("total",pageInfo.getTotal ());
        map.put ("items",issueList);
        return map;
    }

    @Override
    public boolean addIssue(Issue issue) {

        issue.setAddTime (new Date ());
        issue.setUpdateTime (new Date ());
        issue.setDeleted (false);
        int i = issueMapper.insert (issue);
        return i==1;
    }

    @Override
    public int updateIssue(Issue issue) {
        Issue iss=issueMapper.selectByPrimaryKey (issue.getId ());
        if(issue.getQuestion ().equals (iss.getQuestion ()) &&
                issue.getAnswer ().equals (iss.getAnswer ())){
            return 2;
        }
        int i = issueMapper.updateByPrimaryKey (issue);
        if(i==1){
            return 1;
        }
        return 3;


    }

    @Override
    public boolean deleteIssue(Issue issue) {
        issue.setDeleted (true);
        int i = issueMapper.updateByPrimaryKey (issue);
        return i==1;
    }
}

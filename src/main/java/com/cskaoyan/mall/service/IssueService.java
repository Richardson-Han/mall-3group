package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Issue;

import java.util.Map;

/* * 
@author  Walker-胡
@create 2020-06-29 17:19
*/
public interface IssueService {


    Map<String, Object> queryIssueList(Integer page, Integer limit, String sort, String order, String question);

    boolean addIssue(Issue issue);

    int updateIssue(Issue issue);

    boolean deleteIssue(Issue issue);
}

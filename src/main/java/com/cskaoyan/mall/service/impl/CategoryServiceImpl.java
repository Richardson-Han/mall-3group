package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.bean.CategoryExample;
import com.cskaoyan.mall.bean.CategoryVo;
import com.cskaoyan.mall.mapper.CategoryMapper;
import com.cskaoyan.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/* *
@author  Walker-胡
@create 2020-06-28 21:35
*/
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public List<CategoryVo> queryL1() {
     List<CategoryVo> l1VoList = new ArrayList<> ();
        CategoryExample l1categoryExample = new CategoryExample ();
        l1categoryExample.createCriteria ().andDeletedEqualTo (false).andLevelEqualTo ("L1");
        List<Category> l1List =  categoryMapper.selectByExample (l1categoryExample);
        for (Category category : l1List) {
            CategoryVo categoryVo = new CategoryVo ();
            categoryVo.setValue (category.getId ());
            categoryVo.setLabel (category.getName ());
            l1VoList.add (categoryVo);
        }
        return l1VoList;
    }

    @Override
    public List<Category> queryList() {
        CategoryExample l1categoryExample = new CategoryExample ();
        l1categoryExample.createCriteria ().andDeletedEqualTo (false).andLevelEqualTo ("L1");
        List<Category> l1List =  categoryMapper.selectByExample (l1categoryExample);

        CategoryExample l2categoryExample = new CategoryExample ();
        l1categoryExample.createCriteria ().andDeletedEqualTo (false).andLevelEqualTo ("L2");
        List<Category> l2List =  categoryMapper.selectByExample (l2categoryExample);
        for (int i = 0; i <l1List.size () ; i++) {
            for (int j = 0; j <l2List.size () ; j++) {
                Category one=l1List.get (i);
                Category two = l2List.get (j);
                if (two.getPid ().equals (one.getId ())){
                    one.getChildren().add (two);
                }
            }
        }
        return l1List;
    }
//新增目录，若发现同级目录下，商品类别有重名，则返回2；新增成功返回1，失败返回3
    @Override
    public int addCategory(Category category) {
        String level=category.getLevel ();
        //Objects.equals (level,"L1")
        if("L1".equals (level)){
            CategoryExample l1categoryExample = new CategoryExample ();
            l1categoryExample.createCriteria ().andDeletedEqualTo (false).andLevelEqualTo ("L1");
            List<Category> l1List =  categoryMapper.selectByExample (l1categoryExample);
            for (Category category1 : l1List) {
                if(category1.getName ().equals (category.getName ())){
                    return 2;
                }
            }
        }
        //Objects.equals ("L2",level)
        if("L2".equals (level)){
            CategoryExample l2categoryExample = new CategoryExample ();
            l2categoryExample.createCriteria ().andDeletedEqualTo (false).andLevelEqualTo ("L2");
            List<Category> l2List =  categoryMapper.selectByExample (l2categoryExample);
            for (Category category1 : l2List) {
                if(category1.getName ().equals (category.getName ())){
                    return 2;
                }
            }
        }
        category.setDeleted (false);
        category.setAddTime (new Date ());
        category.setUpdateTime (new Date ());
       int i= categoryMapper.insert (category);
       if(i!=1){return 3;}
        return 1;
    }
/*
* 更新目录，如果同级目录发现更新的名字有冲突，则返回2，表示类别名称重复；更新成功返回1；失败返回3
* */
    @Override
    public int updateCategory(Category category) {
        String level=category.getLevel ();

        CategoryExample categoryExample = new CategoryExample ();
        categoryExample.createCriteria ().andDeletedEqualTo (false).andLevelEqualTo (level);
        List<Category> lists =  categoryMapper.selectByExample (categoryExample);
        for (Category category1 : lists) {
            if(category1.getName ().equals (category.getName ())){return 2;}
        }

        category.setUpdateTime (new Date ());
        if (categoryMapper.updateByPrimaryKey (category)==1){
            return 1;
        }
        return 3;
    }
/*
* 如果将一级目录删除，则一级目录下所有的二级目录逻辑上也要删除
* */
    @Override
    public boolean deleteCategory(Category category) {
        if(!category.getChildren ().isEmpty ()&&category.getChildren ().size ()!=0){
              List<Category> lists = category.getChildren ();
            //List<Category> lists=new ArrayList<> ();
          //  BeanUtils.copyProperties (category.getChildren (),lists);
            for (Category child : lists) {
                  child.setUpdateTime (new Date ());
                  child.setDeleted (true);
                  categoryMapper.updateByPrimaryKey (child);
            }
        }
        category.setDeleted (true);
        category.setUpdateTime (new Date ());
        int i = categoryMapper.updateByPrimaryKey (category);
        return i==1;
    }
}

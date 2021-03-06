package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Permission;
import com.cskaoyan.mall.bean.PermissionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.List;

public interface PermissionMapper {
    long countByExample(PermissionExample example);

    int deleteByExample(PermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByExample(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<String> selectPermission(Integer roleId);

    @Select("select permission from cskaoyanmall_permission where role_id = #{roleId} and permission is not null and deleted = 0")
    List<String> selectPermissionByRoleId(@Param("roleId") int roleId);

}
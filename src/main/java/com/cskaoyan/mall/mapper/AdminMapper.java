package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Admin;
import com.cskaoyan.mall.bean.AdminExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper {

    long countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    Integer getLastInsertId();

    @Select("select password from cskaoyanmall_admin where username = #{username}")
    List<String> selectPasswordByName(@Param("username") String username);

    @Select("SELECT role_ids FROM cskaoyanmall_admin WHERE username = #{username}")
    String selectRoleidByUsername(@Param("username") String username);

    @Select("SELECT permission FROM cskaoyanmall_role r\n" +
            "LEFT JOIN cskaoyanmall_permission p ON p.role_id = r.`id` WHERE r.`id` = #{roleid}")
    String[] selectPermissionByRoleid(@Param("roleid") String roleid);

    @Select("select id from cskaoyanmall_role")
    String[] selectAllRoleid();

    Integer updateByUsername(@Param("username") String username, @Param("password") String password);
}
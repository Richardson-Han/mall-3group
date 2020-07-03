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

    @Select("SELECT role_ids FROM cskaoyanmall_admin WHERE username = #{username} and deleted = 0")
    String selectRoleidByUsername(@Param("username") String username);

    @Select("select permission from cskaoyanmall_role r left join " +
            "cskaoyanmall_permission p on p.role_id = r.`id` where r.`id` = #{roleid} " +
            "and p.permission is not null")
    String[] selectPermissionByRoleid(@Param("roleid") String roleid);

    @Select("select id from cskaoyanmall_role")
    String[] selectAllRoleid();

    Integer updateByUsername(@Param("username") String username, @Param("password") String password);

    @Select("select avatar from cskaoyanmall_admin where username = #{username} and deleted = 0")
    String selectAvatarByUsername(@Param("username") String username);

    @Select("select LAST_INSERT_ID()")
    Integer selectLastId();
}
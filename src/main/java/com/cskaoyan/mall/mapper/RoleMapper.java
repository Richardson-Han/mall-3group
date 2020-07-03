package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Role;
import com.cskaoyan.mall.bean.RoleExample;
import com.cskaoyan.mall.bean.VO.RoleOptionsVO;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface RoleMapper {

    List<RoleOptionsVO> selectRoleOptions();

    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    Integer getLastId();

    List<Role> selectInId(@Param("roleIdList") List<Integer> roleIdList);
}
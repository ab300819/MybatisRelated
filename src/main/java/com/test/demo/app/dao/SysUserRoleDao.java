package com.test.demo.app.dao;


import com.test.demo.app.dto.SysUserRoleDto;

public interface SysUserRoleDao {
    int insert(SysUserRoleDto record);

    int insertSelective(SysUserRoleDto record);
}
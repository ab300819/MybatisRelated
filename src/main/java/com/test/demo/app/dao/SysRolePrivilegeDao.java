package com.test.demo.app.dao;

import com.test.demo.app.dto.SysRolePrivilegeDto;

public interface SysRolePrivilegeDao {
    int insert(SysRolePrivilegeDto record);

    int insertSelective(SysRolePrivilegeDto record);
}
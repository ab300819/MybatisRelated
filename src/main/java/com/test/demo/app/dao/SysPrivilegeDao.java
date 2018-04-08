package com.test.demo.app.dao;

import com.test.demo.app.dto.SysPrivilegeDto;

public interface SysPrivilegeDao {
    int deleteByPrimaryKey(Long id);

    int insert(SysPrivilegeDto record);

    int insertSelective(SysPrivilegeDto record);

    SysPrivilegeDto selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPrivilegeDto record);

    int updateByPrimaryKey(SysPrivilegeDto record);
}
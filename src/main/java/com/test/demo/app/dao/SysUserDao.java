package com.test.demo.app.dao;

import com.test.demo.app.dto.SysUserDto;

//@CacheNamespaceRef(SysUserDao.class)
public interface SysUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserDto record);

    int insertSelective(SysUserDto record);

    SysUserDto selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserDto record);

    int updateByPrimaryKey(SysUserDto record);
}
package com.test.demo.app.dao;

import com.test.demo.app.dto.SysRoleDto;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface SysRoleDao {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleDto record);

    int insertSelective(SysRoleDto record);

    Map<String, Object> selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleDto record);

    int updateByPrimaryKey(SysRoleDto record);

    List<SysRoleDto> selectAll(RowBounds rowBounds);
}
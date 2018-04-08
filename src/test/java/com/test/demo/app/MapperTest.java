package com.test.demo.app;

import com.test.demo.app.dao.SysRoleDao;
import com.test.demo.app.dao.SysUserDao;
import com.test.demo.app.dto.SysRoleDto;
import com.test.demo.app.dto.SysUserDto;
import com.test.demo.plugin.PageRowBounds;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

/**
 * Project: ExerciseTimer
 * Package: com.exercise.app
 * Author: mason
 * Time: 15:04
 * Date: 2018-03-12
 * Created with IntelliJ IDEA
 */
public class MapperTest {

    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSelectAll() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {

            SysUserDao sysUserDao = sqlSession.getMapper(SysUserDao.class);

            SysUserDto countryDtoList = sysUserDao.selectByPrimaryKey(1001L);

            System.out.println(countryDtoList.getId() + "-" + countryDtoList.getUserName());

        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testCache() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SysUserDto user1 = null;

        try {

            SysUserDao sysUserDao = sqlSession.getMapper(SysUserDao.class);
            user1 = sysUserDao.selectByPrimaryKey(1L);
            user1.setUserName("New Name");
            SysUserDto user2 = sysUserDao.selectByPrimaryKey(1L);
            assertEquals("New Name", user2.getUserName());
            assertEquals(user1, user2);

        } finally {
            sqlSession.close();
        }
        System.out.println("开启新的sqlSession");
        sqlSession = sqlSessionFactory.openSession();
        try {

            SysUserDao sysUserDao = sqlSession.getMapper(SysUserDao.class);
            SysUserDto user3 = sysUserDao.selectByPrimaryKey(1L);
            user3.setUserName("New Name");
            SysUserDto user4 = sysUserDao.selectByPrimaryKey(1L);
            assertEquals("New Name", user4.getUserName());
            assertEquals(user3, user4);

        } finally {
            sqlSession.close();
        }

    }

    @Test
    public void testInterceptor() {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            SysRoleDao sysRoleDao = sqlSession.getMapper(SysRoleDao.class);
            Map<String, Object> result = sysRoleDao.selectByPrimaryKey(2L);
            result.forEach((o1, o2) -> System.out.println(o1 + " : " + o2));

        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAllByRowBounds() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {

            SysRoleDao sysRoleDao = sqlSession.getMapper(SysRoleDao.class);
            List<SysRoleDto> result = sysRoleDao.selectAll(new RowBounds(0, 1));
            assertNotNull(result);
            result.forEach(t -> System.out.println(t.getRoleName()));

            PageRowBounds pageRowBounds = new PageRowBounds(0, 1);
            result = sysRoleDao.selectAll(pageRowBounds);
            System.out.println("查询总数：" + pageRowBounds.getTotal());
            result.forEach(t -> System.out.println(t.getRoleName()));
            pageRowBounds = new PageRowBounds(1, 1);
            result = sysRoleDao.selectAll(pageRowBounds);
            System.out.println("查询总数：" + pageRowBounds.getTotal());
            result.forEach(t -> System.out.println(t.getRoleName()));
        } finally {
            sqlSession.close();
        }
    }

}

package com.test.demo.app;

import com.test.demo.app.dto.CountryDto;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class DatabaseTest {

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
            List<CountryDto> countryDtoList = sqlSession.selectList("selectAll");
            countryDtoList
                    .stream()
                    .forEach(i -> System.out.printf("%-4d%4s%4s\n", i.getId(), i.getCountryname(), i.getCountrycode()));
        } finally {
            sqlSession.close();
        }
    }

}
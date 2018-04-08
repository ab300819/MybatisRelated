package com.test.demo.plugin;

import com.test.demo.app.dao.CountryDao;
import com.test.demo.app.dto.CountryDto;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.LoggingCache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.decorators.SerializedCache;
import org.apache.ibatis.cache.decorators.SynchronizedCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;


public class SimpleTest {

    @Test
    public void test() throws IOException, SQLException {

        // 配置日志
        LogFactory.useCommonsLogging();

        // 创建配置对象
        final Configuration config = new Configuration();
        // 配置 settings 中属性
        config.setCacheEnabled(true);
        config.setLazyLoadingEnabled(false);
        config.setAggressiveLazyLoading(true);

        // 添加拦截器
        SimpleInterceptor interceptor1 = new SimpleInterceptor("拦截器1");
        SimpleInterceptor interceptor2 = new SimpleInterceptor("拦截器2");

        config.addInterceptor(interceptor1);
        config.addInterceptor(interceptor2);

        // 创建 DataSource
        UnpooledDataSource dataSource = new UnpooledDataSource();
        dataSource.setDriver("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://218.94.66.98:53307/meng_test");
        dataSource.setUsername("root");
        dataSource.setPassword("qnsoft");

        // 创建 JDBC 事务
        Transaction transaction = new JdbcTransaction(dataSource, null, false);

        // 创建 Executor
        //config.newExecutor 会将符合条件的拦截器添加到 Executor 代理链上
        final Executor executor = config.newExecutor(transaction);

        // 类型处理注册器
        final TypeHandlerRegistry registry = config.getTypeHandlerRegistry();

        // 创建SqlSource
        StaticSqlSource sqlSource = new StaticSqlSource(
                config,
                "SELECT * FROM country WHERE id = ?"
        );

        List<ParameterMapping> parameterMappings = new ArrayList<>();
        parameterMappings.add(new ParameterMapping.Builder(
                config,
                "id",
                registry.getTypeHandler(Long.class)
        ).build());

        ParameterMap.Builder paramBuilder = new ParameterMap.Builder(
                config,
                "defaultParameterMap",
                CountryDto.class,
                parameterMappings
        );

        // 创建结果映射
        ResultMap resultMap = new ResultMap.Builder(
                config,
                "defaultResultMap",
                CountryDto.class,
                new ArrayList<ResultMapping>() {{
                    add(new ResultMapping.Builder(config, "id", "id", Long.class).build());
                    add(new ResultMapping.Builder(config, "countryname", "countryname", String.class).build());
                    add(new ResultMapping.Builder(config, "countrycode", "countrycode", registry.getTypeHandler(String.class)).build());
                }}
        ).build();

        // 创建缓存对象
        final Cache countryCache = new SynchronizedCache(
                new SerializedCache(
                        new LoggingCache(
                                new LruCache(
                                        new PerpetualCache("country_cache")))));

        // 创建 MappedStatement 对象
        MappedStatement.Builder msBuilder = new MappedStatement.Builder(
                config,
                "com.exercise.app.dao.CountryDao.selectCountry",
                sqlSource,
                SqlCommandType.SELECT
        );
        msBuilder.parameterMap(paramBuilder.build());
        List<ResultMap> resultMaps = new ArrayList<>();
        resultMaps.add(resultMap);
        // 设置返回值 resultMap
        msBuilder.resultMaps(resultMaps);
        // 设置缓存
        msBuilder.cache(countryCache);
        // 创建
        MappedStatement ms = msBuilder.build();

        List<CountryDto> countries = executor.query(
                ms, 1L, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);

        config.addMappedStatement(ms);
        SqlSession sqlSession = new DefaultSqlSession(config, executor, false);

        CountryDto countryDto1 = sqlSession.selectOne("com.exercise.app.dao.CountryDao.selectCountry", 2L);
        assertNotNull(countryDto1);

        MapperProxyFactory<CountryDao> mapperProxyFactory = new MapperProxyFactory<>(CountryDao.class);
        CountryDao countryDao = mapperProxyFactory.newInstance(sqlSession);
        CountryDto countryDto2 = countryDao.selectCountry(2L);
        assertNotNull(countryDto2);
    }

}
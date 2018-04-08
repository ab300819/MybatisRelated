package com.test.demo.plugin;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.*;

@Intercepts({
        @Signature(
                type = ResultSetHandler.class,
                method = "handleResultSets",
                args = {Statement.class}
        )
})
public class MybatisPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        System.out.println("Begin Interceptor");
        List<Object> result = (List<Object>) invocation.proceed();
        result.forEach(value -> {
            if (value instanceof Map) {
                System.out.println("\u001b[31m This a map \u001b[0m");
                processMap((Map) value);

            }
        });

        return result;
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("Begin Plugin");
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

        String str1 = properties.getProperty("hello");
        String str2 = properties.getProperty("world");

        System.out.println(str1 + " " + str2);

    }

    private void processMap(Map<String, Object> data) {

        Set<String> keys = new HashSet<>(data.keySet());
        keys.forEach(key -> {
            if (key.charAt(0) > 'A' && key.charAt(0) < 'Z' || key.contains("_")) {
                Object value = data.get(key);
                data.remove(key);
                data.put(convertCamelhump(key), value);
            }
        });

    }

    private String convertCamelhump(String key) {

        StringBuilder sb = new StringBuilder();
        boolean nextNeedUpperCase = false;
        for (int i = 0; i < key.length(); i++) {
            char str = key.charAt(i);
            if (str == '_') {
                if (sb.length() > 0) {
                    nextNeedUpperCase = true;
                }
            } else {
                if (nextNeedUpperCase) {
                    sb.append(Character.toUpperCase(str));
                    nextNeedUpperCase = false;
                } else {
                    sb.append(Character.toLowerCase(str));
                }
            }
        }

        return sb.toString();
    }
}

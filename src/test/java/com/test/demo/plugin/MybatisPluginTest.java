package com.test.demo.plugin;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class MybatisPluginTest {

    public static MybatisPlugin mybatisPlugin;

    @BeforeClass
    public static void setUp() throws Exception {
        mybatisPlugin = new MybatisPlugin();
    }

    @Test
    public void testConvertCamelhump() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String test1 = "Affff_fsdf_opp";
        String excep1 = "affffFsdfOpp";
        String test2 = "affff_fsdf_opp";
        String excep2 = "affffFsdfOpp";

        Method convertCamelhump = MybatisPlugin.class.getDeclaredMethod("convertCamelhump", String.class);
        convertCamelhump.setAccessible(true);

        String result1 = (String) convertCamelhump.invoke(mybatisPlugin, test1);
        String result2 = (String) convertCamelhump.invoke(mybatisPlugin, test2);

        assertEquals(excep1, result1);
        assertEquals(excep2, result2);
    }


}
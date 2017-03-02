package com.lenovo.newdevice.car.server;

import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * Created @2017/3/2 12:32
 */
public class LoggerTest {

    @Test
    public void testLogger() {
        printLog();
    }

    private static void printLog() {
        System.setProperty("log4j.configuration", "log4j.properties");
        LoggerFactory.getLogger(LoggerTest.class).info("XXXX");
        LoggerFactory.getLogger(LoggerTest.class).debug("XXXX");
        LoggerFactory.getLogger(LoggerTest.class).warn("XXXX");
        LoggerFactory.getLogger(LoggerTest.class).error("XXXX");
    }
}

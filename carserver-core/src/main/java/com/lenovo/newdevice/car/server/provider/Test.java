package com.lenovo.newdevice.car.server.provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created @2017/2/23 11:21
 */
public class Test {

    private Logger sLogger = LogManager.getLogger();

    public void loadSettings() {

        Resource resource = new ClassPathResource("settings.properties");
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                sLogger.info(key + "-" + value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.lenovo.newdevice.car.server.provider;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created @2017/2/27 13:33
 */
public class PropertySettings implements Settings {

    @Getter
    private Properties properties;

    public PropertySettings(String prop) throws IOException {
        Resource resource = new ClassPathResource(prop);
        this.properties = PropertiesLoaderUtils.loadProperties(resource);
    }

    private void ensureProperties() {
        Preconditions.checkNotNull(properties, "Init err");
    }

    @Override
    public String get(@NonNull String key, String defValue) {
        ensureProperties();
        String out = (String) properties.get(key);
        return out == null ? defValue : out;
    }

    @Override
    public void set(@NonNull String key, String value) {
        ensureProperties();
        properties.put(key, value);
    }
}

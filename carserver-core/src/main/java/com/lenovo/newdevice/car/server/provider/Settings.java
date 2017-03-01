package com.lenovo.newdevice.car.server.provider;

import lombok.NonNull;

/**
 * Created @2017/2/23 11:13
 */
public interface Settings {

    String get(@NonNull String key, String defValue);

    void set(@NonNull String key, String value);
}

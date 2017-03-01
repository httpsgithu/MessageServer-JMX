package com.lenovo.newdevice.carserver.api.model;

import lombok.NonNull;

/**
 * Created @2017/2/28 17:50
 */
public interface JsonStringSerializable {
    @NonNull
    String toJsonString();
}

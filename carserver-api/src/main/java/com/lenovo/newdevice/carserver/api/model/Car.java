package com.lenovo.newdevice.carserver.api.model;

import com.google.gson.Gson;
import lombok.*;

import java.io.Serializable;

/**
 * Created @2017/2/27 15:23
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Car implements Serializable, JsonStringSerializable, BytesSerializable {

    private String name;

    private String uuid;

    @NonNull
    @Override
    public String toJsonString() {
        return new Gson().toJson(this);
    }

    @NonNull
    @Override
    public byte[] toBytes() {
        return toJsonString().getBytes();
    }

    public static Car from(@NonNull String src) {
        return new Gson().fromJson(src, Car.class);
    }
}

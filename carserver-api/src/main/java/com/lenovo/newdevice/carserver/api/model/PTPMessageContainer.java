package com.lenovo.newdevice.carserver.api.model;

import com.google.gson.Gson;
import lombok.*;

import java.io.Serializable;

/**
 * Created @2017/2/28 11:50
 */

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class PTPMessageContainer implements Serializable, JsonStringSerializable, BytesSerializable {

    private Car target;
    private String content;

    @Override
    public byte[] toBytes() {
        return toJsonString().getBytes();
    }

    @Override
    public String toJsonString() {
        return new Gson().toJson(this);
    }

    public static PTPMessageContainer from(@NonNull String src) {
        return new Gson().fromJson(src, PTPMessageContainer.class);
    }
}

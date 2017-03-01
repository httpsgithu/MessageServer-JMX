package com.lenovo.newdevice.car.server.network;

import lombok.NonNull;

import javax.jms.Message;

/**
 * Created @2017/2/28 14:58
 */
public interface BroadcastReceiver {
    void onReceive(@NonNull Message message);
}

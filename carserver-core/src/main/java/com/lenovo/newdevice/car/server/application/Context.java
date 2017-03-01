package com.lenovo.newdevice.car.server.application;

import com.lenovo.newdevice.car.server.network.BroadcastReceiver;
import com.lenovo.newdevice.car.server.network.Broadcaster;
import com.lenovo.newdevice.car.server.network.ClientManager;
import com.lenovo.newdevice.car.server.network.MessageBroker;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Context {

    @NonNull
    ClientManager getClientManager();

    @NonNull
    MessageBroker getMessageBroker();

    @NonNull
    Broadcaster getBroadcaster();

    boolean registerReceiver(@NonNull BroadcastReceiver receiver);

    default Logger defaultLogger() {
        return LogManager.getLogger(Context.class);
    }
}

package com.lenovo.newdevice.car.server.application;

import com.lenovo.newdevice.car.server.network.*;
import lombok.NonNull;

public interface Context {

    @NonNull
    ClientManager getClientManager();

    @NonNull
    MessageSender getMessageSender();

    @NonNull
    Broadcaster getBroadcaster();

    @NonNull
    BrokerManager getBrokerManager();

    boolean registerReceiver(@NonNull BroadcastReceiver receiver);
}

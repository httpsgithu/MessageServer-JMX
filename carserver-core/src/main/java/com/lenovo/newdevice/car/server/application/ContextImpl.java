package com.lenovo.newdevice.car.server.application;

import com.lenovo.newdevice.car.server.network.BroadcastReceiver;
import com.lenovo.newdevice.car.server.network.Broadcaster;
import com.lenovo.newdevice.car.server.network.ClientManager;
import com.lenovo.newdevice.car.server.network.MessageBroker;
import com.lenovo.newdevice.car.server.network.impl.BroadcastReceiverRegister;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created @2017/2/23 11:12
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ContextImpl implements Context {

    @Getter
    @Autowired
    private
    ClientManager clientManager;

    @Getter
    @Autowired
    private
    MessageBroker messageBroker;

    @Getter
    @Autowired
    private
    Broadcaster broadcaster;

    @Autowired
    private BroadcastReceiverRegister receiverRegister;

    @Override
    public boolean registerReceiver(@NonNull BroadcastReceiver receiver) {
        return receiverRegister.register(receiver);
    }
}

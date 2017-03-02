package com.lenovo.newdevice.car.server.application;

import com.lenovo.newdevice.car.server.network.*;
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
    MessageSender messageSender;

    @Getter
    @Autowired
    private
    Broadcaster broadcaster;

    @Getter
    @Autowired
    BrokerManager brokerManager;

    @Autowired
    private BroadcastReceiverRegister receiverRegister;

    @Override
    public boolean registerReceiver(@NonNull BroadcastReceiver receiver) {
        return receiverRegister.register(receiver);
    }
}

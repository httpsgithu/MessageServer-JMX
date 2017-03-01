package com.lenovo.newdevice.car.server.network.impl;

import com.lenovo.newdevice.car.server.network.BroadcastReceiver;
import com.lenovo.newdevice.car.server.provider.NetServerSettings;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Created @2017/2/28 14:51
 */
@NoArgsConstructor
public class BroadcastReceiverRegister extends MQBasedActivity {

    BroadcastReceiverRegister(NetServerSettings serverSettings) {
        super(serverSettings);
    }

    public boolean register(@NonNull BroadcastReceiver receiver) {
        try {
            Session session = createSession(null);
            createConsumer(session, getServerSettings().getMessageBoadcastTopic(), receiver::onReceive);
            return true;
        } catch (JMSException e) {
            getLogger().error(e);
            return false;
        }
    }
}

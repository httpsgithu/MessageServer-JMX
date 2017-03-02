package com.lenovo.newdevice.car.server.network.impl;

import com.lenovo.newdevice.car.server.network.BroadcastReceiver;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Created @2017/2/28 14:51
 */
@NoArgsConstructor
public class BroadcastReceiverRegister extends MQBasedActivity {

    public boolean register(@NonNull BroadcastReceiver receiver) {
        try {
            Session session = createSession(null);
            createConsumer(session, getServerSettings().getMessageBoadcastTopic(), receiver::onReceive);
            return true;
        } catch (JMSException e) {
            getLogger().error("register", e);
            return false;
        }
    }
}

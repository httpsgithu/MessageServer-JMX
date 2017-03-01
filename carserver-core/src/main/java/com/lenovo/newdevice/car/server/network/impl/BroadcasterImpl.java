package com.lenovo.newdevice.car.server.network.impl;

import com.lenovo.newdevice.car.server.network.Broadcaster;
import com.lenovo.newdevice.car.server.provider.NetServerSettings;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

/**
 * Created @2017/2/28 12:09
 */
@NoArgsConstructor
public class BroadcasterImpl extends MQBasedActivity implements Broadcaster {

    @Getter
    private MessageProducer producer;

    private Session session;

    public BroadcasterImpl(NetServerSettings serverSettings) {
        super(serverSettings);
    }

    @Override
    public boolean onStart() {
        try {
            session = createSession(getServerSettings().getMessageBroadcasterId());
            producer = createProducer(session, getServerSettings().getMessageBoadcastTopic());
        } catch (JMSException e) {
            getLogger().error(e);
            return false;
        }

        return true;
    }

    @Override
    public void onDestroy() {
        close(producer);
        close(session);
        closeConnection();
    }

    @Override
    public boolean sendBroadcast(@NonNull Message message) {
        try {
            getProducer().send(message);
            return true;
        } catch (JMSException e) {
            getLogger().error(e);
            return false;
        }
    }
}

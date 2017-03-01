package com.lenovo.newdevice.car.server.network.impl;

import com.lenovo.newdevice.car.server.provider.NetServerSettings;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

import static com.lenovo.newdevice.car.server.network.impl.TestUtils.println;

/**
 * Created @2017/2/23 15:01
 */
public class Consumer {

    private NetServerSettings serverSettings;
    private transient Connection connection;
    private transient Session session;

    public Consumer(String id) throws JMSException {
        try {
            serverSettings = new NetServerSettings();
            ConnectionFactory factory = new ActiveMQConnectionFactory(serverSettings.getBrokerUrl());
            connection = factory.createConnection();
            connection.setClientID(id);
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (IOException e) {

        }

    }

    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws JMSException {
        new Consumer("XXXXX-YYYYY").subscribe();
        new Consumer("XXXXX-YYYYY-1").subscribe();
        new Consumer("XXXXX-YYYYY-2").subscribe();
        new Consumer("XXXXX-YYYYY-3").subscribe();
    }

    private void subscribe() throws JMSException {
        Destination destination = getSession().createTopic(serverSettings.getMessageReceiverTopic());
        MessageConsumer messageConsumer = getSession().createConsumer(destination);
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                println("onMessage:" + message);
            }
        });
    }

    public Session getSession() {
        return session;
    }
}

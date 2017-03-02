package com.lenovo.newdevice.car.server.network.impl;

import com.lenovo.newdevice.car.server.message.MessageHelper;
import com.lenovo.newdevice.car.server.provider.NetWorkSettings;
import com.lenovo.newdevice.carserver.api.model.Car;
import com.lenovo.newdevice.carserver.api.model.PTPMessageContainer;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * Created @2017/2/23 14:10
 */
public class Publisher {

    protected static transient ConnectionFactory factory;
    protected transient Connection connection;
    protected transient Session session;
    protected transient MessageProducer producer;

    private NetWorkSettings serverSettings;

    public Publisher() throws JMSException {
        try {
            serverSettings = new NetWorkSettings();
            factory = new ActiveMQConnectionFactory(serverSettings.getBrokerUrl());
            connection = factory.createConnection();
            try {
                connection.start();
            } catch (JMSException jmse) {
                connection.close();
                throw jmse;
            }
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws JMSException {
        Publisher publisher = new Publisher();
        publisher.sendMessage();
        publisher.close();
    }

    protected void sendMessage() throws JMSException {
        Destination destination = session.createTopic(serverSettings.getMessageSenderTopic());
        Message message = createStockMessage(session);
        message.setStringProperty(serverSettings.getPTPClientIDPropertyName(), serverSettings.getMessageBrokerId());
        System.out.println("Sending: " + message + " on destination: " + destination);
        producer.send(destination, message);
    }

    protected Message createStockMessage(Session session) throws JMSException {
        PTPMessageContainer container = new PTPMessageContainer();
        container.setContent("Hello!");
        Car target = new Car();
        target.setUuid("Android-XXXX");
        container.setTarget(target);
        return MessageHelper.createByteMessage(session, container);
    }
}

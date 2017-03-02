package com.lenovo.newdevice.car.server.network;

import com.lenovo.newdevice.car.server.message.MessageHelper;
import com.lenovo.newdevice.car.server.provider.NetWorkSettings;
import com.lenovo.newdevice.carserver.api.model.Car;
import lombok.Getter;
import lombok.Setter;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.UUID;

/**
 * Created @2017/2/27 14:32
 */
public class DummyCar {

    @Setter
    @Getter
    private String carName;

    private NetWorkSettings serverSettings;

    protected static transient ConnectionFactory factory;
    protected transient Connection connection;
    protected transient Session session;
    protected transient MessageProducer producer;

    public DummyCar(NetWorkSettings serverSettings) throws JMSException {
        this.serverSettings = serverSettings;
        factory = new ActiveMQConnectionFactory(serverSettings.getBrokerUrl());
        connection = factory.createConnection();
        connection.setClientID(getCarName());
        try {
            connection.start();
        } catch (JMSException jmse) {
            connection.close();
            throw jmse;
        }
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(null);
    }

    public static void main(String... args) {
        try {
            DummyCar car = new DummyCar(new NetWorkSettings());
            car.setCarName("Car-X");
            car.onLine();
        } catch (JMSException | IOException e) {
            e.printStackTrace();
        }
    }

    public void onLine() {
        try {
            sendLoginMessage();
            sendLoginMessageHeadless();
            sendLoginMessageNoId();

            sendLogoutMessage();
            sendLogoutMessageHeadless();
            sendLogoutMessageNoId();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }

    protected void sendLoginMessage() throws JMSException {
        Destination destination = session.createTopic(serverSettings.getCarLoginTopic());
        Message message = createLoginMessage(session);
        message.setStringProperty(serverSettings.getPTPClientIDPropertyName(), serverSettings.getCarListManagerId());
        producer.send(destination, message);
    }

    protected void sendLoginMessageNoId() throws JMSException {
        Destination destination = session.createTopic(serverSettings.getCarLoginTopic());
        Message message = createLoginMessage(session);
        producer.send(destination, message);
    }

    protected void sendLoginMessageHeadless() throws JMSException {
        Destination destination = session.createTopic(serverSettings.getCarLoginTopic());
        Message message = createLoginMessage(session);
        message.setStringProperty(serverSettings.getPTPClientIDPropertyName(), "Noop");
        producer.send(destination, message);
    }

    protected void sendLogoutMessage() throws JMSException {
        Destination destination = session.createTopic(serverSettings.getCarLogoutTopic());
        Message message = createLogoutMessage(session);
        message.setStringProperty(serverSettings.getPTPClientIDPropertyName(), serverSettings.getCarListManagerId());
        producer.send(destination, message);
    }

    protected void sendLogoutMessageNoId() throws JMSException {
        Destination destination = session.createTopic(serverSettings.getCarLogoutTopic());
        Message message = createLogoutMessage(session);
        producer.send(destination, message);
    }

    protected void sendLogoutMessageHeadless() throws JMSException {
        Destination destination = session.createTopic(serverSettings.getCarLogoutTopic());
        Message message = createLogoutMessage(session);
        message.setStringProperty(serverSettings.getPTPClientIDPropertyName(), "Noop");
        producer.send(destination, message);
    }

    protected Message createLoginMessage(Session session) throws JMSException {
        Car car = new Car();
        car.setUuid(UUID.randomUUID().toString());
        car.setName(getCarName());
        return MessageHelper.createByteMessage(session, car);
    }

    protected Message createLogoutMessage(Session session) throws JMSException {
        Car car = new Car();
        car.setName(getCarName());
        car.setUuid(UUID.randomUUID().toString());
        return MessageHelper.createByteMessage(session, car);
    }

}

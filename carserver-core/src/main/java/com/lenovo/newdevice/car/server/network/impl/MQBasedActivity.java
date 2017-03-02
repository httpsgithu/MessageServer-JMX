package com.lenovo.newdevice.car.server.network.impl;

import com.lenovo.newdevice.car.server.network.BrokerDetector;
import com.lenovo.newdevice.car.server.network.BrokerManager;
import com.lenovo.newdevice.car.server.provider.NetWorkSettings;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.*;

/**
 * Created @2017/2/28 13:01
 */
@NoArgsConstructor
@Setter
class MQBasedActivity {

    @Getter
    private transient Connection connection;

    @Getter
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Getter
    private
    NetWorkSettings serverSettings;

    @Autowired
    @Getter
    private
    BrokerManager brokerManager;

    Session createSession(String clientID) throws JMSException {

        if (!BrokerDetector.checkBrokerService(brokerManager.getPublishableConnectURI("openwire").toString())) {
            throw new JMSException("Broker not available@" + getServerSettings().getBrokerUrl());
        }

        ConnectionFactory factory = new ActiveMQConnectionFactory(serverSettings.getBrokerUrl());
        connection = factory.createConnection();

        logger.info("clientID:" + clientID);
        if (clientID != null) {
            connection.setClientID(clientID);
        }

        connection.start();
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    MessageConsumer createConsumer(@NonNull Session session, @NonNull String topic, @NonNull MessageListener listener)
            throws JMSException {
        logger.info("creating Topic:" + topic);
        Destination destination = session.createTopic(topic);
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(listener);
        return consumer;
    }

    MessageProducer createProducer(@NonNull Session session, @NonNull String topic) throws JMSException {
        logger.info("creating Topic:" + topic);
        Destination destination = session.createTopic(topic);
        return session.createProducer(destination);
    }

    void closeConnection() {
        logger.info("closing connection.");
        if (connection != null) {
            try {
                connection.stop();
                connection.close();
            } catch (JMSException e) {
                getLogger().warn("Ex when closeConnection:", e);
            }
        }
    }

    void close(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (JMSException e) {
                getLogger().warn("Ex when close session:", e);
            }
        }
    }

    void close(MessageProducer producer) {
        if (producer != null) {
            try {
                producer.close();
            } catch (JMSException e) {
                getLogger().warn("Ex when close producer:", e);
            }
        }
    }

    void close(MessageConsumer consumer) {
        if (consumer != null) {
            try {
                consumer.close();
            } catch (JMSException e) {
                getLogger().warn("Ex when close consumer:", e);
            }
        }
    }
}

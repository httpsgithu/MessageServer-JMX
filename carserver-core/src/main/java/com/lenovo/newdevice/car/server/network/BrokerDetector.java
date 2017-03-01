package com.lenovo.newdevice.car.server.network;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * Created @2017/2/27 13:18
 */
public class BrokerDetector {

    public static boolean checkBrokerService(String url) {

        ConnectionFactory factory = new ActiveMQConnectionFactory(url);
        Connection connection = null;
        try {
            connection = factory.createConnection();
        } catch (JMSException e) {
            return false;
        }
        try {
            connection.start();
        } catch (JMSException jmse) {
            return false;
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (JMSException ignored) {

            }
        }
        return true;
    }
}

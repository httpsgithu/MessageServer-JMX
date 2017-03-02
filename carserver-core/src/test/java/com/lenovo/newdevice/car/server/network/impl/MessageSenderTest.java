package com.lenovo.newdevice.car.server.network.impl;

import com.lenovo.newdevice.car.server.network.BrokerManager;
import com.lenovo.newdevice.car.server.network.MessageSender;
import lombok.Getter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.jms.JMSException;

import static com.lenovo.newdevice.car.server.TestUtils.println;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created @2017/2/28 13:26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfiguration.xml")
public class MessageSenderTest {

    @Resource
    private
    MessageSender messageSender;

    @Resource
    @Getter
    BrokerManager brokerManager;


    @Before
    public void setup() {
        assertTrue(brokerManager.onStart());
        assertTrue(messageSender.onStart());
    }

    @Test
    public void testSend() {
        try {
            Consumer.main(null);
        } catch (JMSException e) {
            fail(e.getLocalizedMessage());
        }
        try {
            Publisher.main(null);
        } catch (JMSException e) {
            fail(e.getLocalizedMessage());
        }
    }

    @After
    public void clean() {
        messageSender.onDestroy();
        brokerManager.onDestroy();
        println();
    }
}
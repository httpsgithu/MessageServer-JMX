package com.lenovo.newdevice.car.server.network.impl;

import com.lenovo.newdevice.car.server.network.Broadcaster;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.lenovo.newdevice.car.server.network.impl.TestUtils.println;
import static org.junit.Assert.assertTrue;

/**
 * Created @2017/2/28 14:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfiguration.xml")
public class BroadcasterTest {

    private Broadcaster broadcaster;

    @Test
    public void dummy() {
    }

    public void startServer() {
        assertTrue(broadcaster.onStart());
    }

    public void testSend() {
        assertTrue(broadcaster.sendBroadcast(new ActiveMQTextMessage()));
    }

    public void shutdown() {
        broadcaster.onDestroy();
        println();
    }
}
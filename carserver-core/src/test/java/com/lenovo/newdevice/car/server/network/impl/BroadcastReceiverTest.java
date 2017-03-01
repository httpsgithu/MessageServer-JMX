package com.lenovo.newdevice.car.server.network.impl;

import com.lenovo.newdevice.car.server.application.Context;
import com.lenovo.newdevice.car.server.network.Broadcaster;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static com.lenovo.newdevice.car.server.network.impl.TestUtils.println;
import static com.lenovo.newdevice.car.server.network.impl.TestUtils.sleep;
import static org.junit.Assert.fail;

/**
 * Created @2017/2/28 14:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfiguration.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BroadcastReceiverTest {

    @Resource
    private Context context;

    @Resource
    private Broadcaster broadcaster;

    @Before
    public void startServer() {
        for (int i = 0; i < 10; i++) {
            if (broadcaster.onStart()) return;
            sleep(1);
        }
        fail();
    }

    @Test
    public void testRegister() {
        context.registerReceiver(message -> println("testRegister, onReceive:" + message));

        broadcaster.sendBroadcast(new ActiveMQTextMessage());
    }

    @After
    public void onEnd() {
        broadcaster.onDestroy();
        println();
    }
}

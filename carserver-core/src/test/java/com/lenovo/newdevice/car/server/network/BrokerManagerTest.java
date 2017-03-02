package com.lenovo.newdevice.car.server.network;

import com.google.common.base.Preconditions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static com.lenovo.newdevice.car.server.TestUtils.println;

/**
 * Created @2017/3/2 10:06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfiguration.xml")
public class BrokerManagerTest {

    @Resource
    private BrokerManager manager;

    @Test
    public void testStart() {

        manager.onStart();
        println(Preconditions.checkNotNull(manager.getPublishableConnectURI("mqtt")));
        println(Preconditions.checkNotNull(manager.getPublishableConnectURI("openwire")));

        Assert.assertTrue(BrokerDetector.checkBrokerService(manager.getPublishableConnectURI("openwire").toString()));
    }

    @After
    public void clean() {
        manager.onDestroy();
    }
}
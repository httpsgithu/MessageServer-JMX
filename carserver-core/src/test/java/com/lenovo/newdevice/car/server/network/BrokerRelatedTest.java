package com.lenovo.newdevice.car.server.network;

import lombok.Getter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.concurrent.atomic.AtomicInteger;

import static com.lenovo.newdevice.car.server.TestUtils.sleep;

/**
 * Created @2017/3/2 10:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfiguration.xml")
public class BrokerRelatedTest {

    public static final AtomicInteger sBrokerId = new AtomicInteger(1000);

    @Resource
    @Getter
    BrokerManager brokerManager;

    public void startBroker() {
        brokerManager.setName("Test-Broker-" + sBrokerId.getAndIncrement());
        brokerManager.onStart();
    }

    public void stopBroker() {
        brokerManager.onDestroy();
    }

    @Before
    public void setup() {
        startBroker();
    }

    @After
    public void clean() {
        stopBroker();
        sleep(3);
    }

    @Test
    public void dummy() {

    }
}

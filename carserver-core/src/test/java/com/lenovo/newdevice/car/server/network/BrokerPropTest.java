package com.lenovo.newdevice.car.server.network;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.xbean.BrokerFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertTrue;

/**
 * Created @2017/3/1 16:46
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfiguration.xml")
public class BrokerPropTest {

    @Resource
    BrokerFactoryBean factoryBean;

    @Test
    public void testStart() {
        BrokerService service = factoryBean.getBroker();
        assertTrue(service != null);
    }

    @Test
    public void testConfig() {
        BrokerService service = factoryBean.getBroker();
        service.setBrokerName("Dummy-B");
    }
}
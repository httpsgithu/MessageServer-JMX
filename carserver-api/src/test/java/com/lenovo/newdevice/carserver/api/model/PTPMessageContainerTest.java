package com.lenovo.newdevice.carserver.api.model;

import org.junit.Test;

import java.util.UUID;

import static com.lenovo.newdevice.carserver.api.TestUtils.println;
import static org.junit.Assert.assertTrue;

/**
 * Created @2017/2/28 17:52
 */
public class PTPMessageContainerTest {

    @Test
    public void testAll() {
        Car car = new Car();
        car.setName("Tester");
        car.setUuid(UUID.randomUUID().toString());

        String js = car.toJsonString();
        println(js);
        assertTrue(js != null);
        byte[] bs = car.toBytes();
        println(bs.length);

        PTPMessageContainer container = new PTPMessageContainer();
        container.setTarget(car);
        container.setContent("Hello test!");
        js = container.toJsonString();
        println(js);
        assertTrue(js != null);
        bs = container.toBytes();
        println(bs.length);

    }
}
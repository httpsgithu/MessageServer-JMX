package com.lenovo.newdevice.car.server;

import org.junit.Test;

import static com.lenovo.newdevice.car.server.TestUtils.postDelayed;
import static org.junit.Assert.assertTrue;

/**
 * Created @2017/3/2 9:44
 */
public class CarControllerTest {

    @Test
    public void testLifeCycle() {
        CarController carController = new CarController();
        postDelayed(carController::onDestroy, 5);
        assertTrue(carController.onStart());
    }
}
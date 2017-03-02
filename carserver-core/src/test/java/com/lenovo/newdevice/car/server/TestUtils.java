package com.lenovo.newdevice.car.server;

import com.lenovo.newdevice.car.server.network.BrokerManager;
import com.lenovo.newdevice.car.server.network.impl.BrokerManagerImpl;
import com.lenovo.newdevice.car.server.utils.ThreadUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created @2017/2/28 14:47
 */
public class TestUtils {

    private static final AtomicInteger sThreadIndex = new AtomicInteger(0);

    public static void println(Object o) {
        System.out.println("----TEST LOG-----" + String.valueOf(o));
    }

    public static void println() {
        System.out.println("--------------------------------------------------");
    }

    public static void printlns(int lines) {
        for (int i = 0; i < lines; i++) {
            System.out.println("--------------------------------------------------");
        }
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void post(Runnable r) {
        ThreadUtils.createStarted(r, "Post-Thread-" + sThreadIndex.getAndIncrement());
    }

    public static void postDelayed(Runnable r, int delay) {
        ThreadUtils.createStarted(() -> {
            sleep(delay);
            r.run();
        }, "Post-Thread-" + sThreadIndex.getAndIncrement());
    }
}

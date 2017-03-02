package com.lenovo.newdevice.car.server.utils;

import com.lenovo.newdevice.car.server.application.Component;
import lombok.NonNull;

/**
 * Created @2017/2/28 12:46
 */
public class ThreadUtils {

    public static Thread createStarted(@NonNull Runnable runnable, String name) {
        Thread thread = new Thread(runnable, name);
        thread.start();
        return thread;
    }

    public static Thread createStarted(@NonNull Component component, String name) {
        Thread thread = new Thread(component::onStart, name);
        thread.start();
        return thread;
    }
}

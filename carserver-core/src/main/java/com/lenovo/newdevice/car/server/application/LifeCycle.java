package com.lenovo.newdevice.car.server.application;

/**
 * Created @2017/2/28 15:38
 */
public interface LifeCycle {
    boolean onStart();

    void onDestroy();
}

package com.lenovo.newdevice.car.server.application;

/**
 * Created @2017/2/23 11:15
 */
public interface Component extends LifeCycle {
    default String componentName() {
        return getClass().getSimpleName();
    }
}

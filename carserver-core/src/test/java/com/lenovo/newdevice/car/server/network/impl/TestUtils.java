package com.lenovo.newdevice.car.server.network.impl;

/**
 * Created @2017/2/28 14:47
 */
public class TestUtils {

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
}

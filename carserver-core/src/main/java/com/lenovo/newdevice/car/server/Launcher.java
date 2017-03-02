package com.lenovo.newdevice.car.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created @17-2-22 11:06
 */
public class Launcher {

    private static final Logger sLogger = LoggerFactory.getLogger(Launcher.class);

    // bla bla bla
    public static void main(String[] args) {
        System.setProperty("log4j.configuration", "log4j.properties");
        sLogger.info("Starting application.");
        boolean ok = new CarController().onStart();
        sLogger.debug("Start up " + (ok ? "success!" : "failure!"));
    }
}

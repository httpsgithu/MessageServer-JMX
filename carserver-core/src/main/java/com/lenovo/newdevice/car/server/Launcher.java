package com.lenovo.newdevice.car.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created @17-2-22 11:06
 */
public class Launcher {

    private static final Logger sLogger = LogManager.getLogger(Launcher.class);

    // bla bla bla
    public static void main(String[] args) {
        sLogger.info("Starting application.");
        boolean ok = new CarController().onStart();
        sLogger.debug("Start up " + (ok ? "success!" : "failure!"));
    }
}

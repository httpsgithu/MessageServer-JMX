package com.lenovo.newdevice.car.server;

import com.lenovo.newdevice.car.server.application.Component;
import com.lenovo.newdevice.car.server.application.Context;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class CarController implements Component {

    @Getter
    private Context context;

    private ServiceKeeper mKeeper;

    public CarController() {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        ApplicationContext springCtx = new ClassPathXmlApplicationContext("file:configs/appConfiguration.xml");
        this.context = (Context) springCtx.getBean("context");
    }

    @Override
    public boolean onStart() {

        Component[] components = new Component[]{
                context.getClientManager(),
                context.getMessageBroker(),
                context.getBroadcaster()
        };

        mKeeper = new ServiceKeeper(components);

        return mKeeper.startKeep();
    }

    @Override
    public void onDestroy() {
        mKeeper.shutdown();
    }

    private class ShutdownHook extends Thread {
        @Override
        public void run() {
            super.run();
            System.err.println("Shutting down...");
        }
    }

    private static class LifeCycleCaller {
        static boolean callStart(Component component) {
            return component.onStart();
        }

        static void callShutdown(Component component) {
            component.onDestroy();
        }
    }

    private class ServiceKeeper {

        CountDownLatch latch = null;

        Component[] components;

        ServiceKeeper(Component[] components) {
            this.components = components;
        }

        boolean startKeep() {
            AtomicBoolean success = new AtomicBoolean(true);
            for (Component c : components) {
                ThreadUtils.createStarted(() -> {
                    boolean ok = LifeCycleCaller.callStart(c);
                    if (!ok) success.set(false);
                    onComponentStartResult(c, ok);
                }, c.toString());
            }

            if (success.get()) {
                waitForShutdown(components.length);
            }
            return success.get();
        }

        void onComponentStartResult(Component component, boolean res) {
            context.defaultLogger().info("onComponentStartResult:" + component.componentName() + "-" + res);
        }

        void shutdown() {
            for (Component s : components) {
                LifeCycleCaller.callShutdown(s);
                shutdownOnce();
            }
        }

        void waitForShutdown(int count) {
            latch = new CountDownLatch(count);
            while (true) {
                try {
                    latch.await();
                    break;
                } catch (InterruptedException ignored) {

                }
            }
        }

        void shutdownOnce() {
            latch.countDown();
        }
    }
}

package com.lenovo.newdevice.car.server;

import com.lenovo.newdevice.car.server.application.Component;
import com.lenovo.newdevice.car.server.application.Context;
import com.lenovo.newdevice.car.server.utils.ThreadUtils;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class CarController implements Component {

    @Getter
    private Context context;

    private ServiceKeeper mKeeper;

    private Logger mLogger;

    public CarController() {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        ApplicationContext springCtx = new ClassPathXmlApplicationContext("classpath:appConfiguration.xml");
        this.context = (Context) springCtx.getBean("context");
        this.mLogger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public boolean onStart() {

        List<Component> all = new ArrayList<>();
        all.add(context.getBrokerManager());
        all.add(context.getClientManager());
        all.add(context.getMessageSender());
        all.add(context.getBroadcaster());

        mKeeper = new ServiceKeeper(all);

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

        static Logger sLogger = LoggerFactory.getLogger(LifeCycleCaller.class);

        static boolean callStart(Component component) {
            sLogger.info("Starting:" + component.componentName());
            return component.onStart();
        }

        static void callShutdown(Component component) {
            sLogger.info("Shutting down:" + component.componentName());
            component.onDestroy();
        }
    }

    private class ServiceKeeper {

        CountDownLatch mLatch = null;

        List<Component> mComponents;

        ServiceKeeper(List<Component> components) {
            this.mComponents = components;
        }

        boolean startKeep() {
            AtomicBoolean success = new AtomicBoolean(true);
            for (Component c : mComponents) {
                ThreadUtils.createStarted(() -> {
                    boolean ok = LifeCycleCaller.callStart(c);
                    if (!ok) success.set(false);
                    onComponentStartResult(c, ok);
                }, c.toString());
            }

            if (success.get()) {
                waitForShutdown(mComponents.size());
            }
            return success.get();
        }

        void onComponentStartResult(Component component, boolean res) {
            mLogger.info("onComponentStartResult:" + component.componentName() + "-" + res);
        }

        void shutdown() {
            Collections.reverse(mComponents);
            for (Component s : mComponents) {
                LifeCycleCaller.callShutdown(s);
                shutdownOnce();
            }
        }

        void waitForShutdown(int count) {
            mLogger.info("Components count:" + count);
            mLatch = new CountDownLatch(count);
            while (true) {
                try {
                    mLatch.await();
                    break;
                } catch (InterruptedException ignored) {

                }
            }
        }

        void shutdownOnce() {
            mLatch.countDown();
        }
    }
}

package com.lenovo.newdevice.car.server.network.impl;

import com.lenovo.newdevice.car.server.network.BrokerManager;
import lombok.NonNull;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * Created @2017/3/2 10:07
 */
public class BrokerManagerImpl implements BrokerManager {

    private Logger gLogger = LoggerFactory.getLogger(BrokerManager.class);

    private BrokerService mBrokerService;

    public BrokerManagerImpl() {
        try {
            mBrokerService = BrokerFactory.createBroker("xbean:activemq.xml");
        } catch (Exception e) {
            gLogger.error("Construct failure", e);
        }
    }

    @Override
    public boolean onStart() {
        try {
            mBrokerService.start();
            mBrokerService.waitUntilStarted();
            return mBrokerService.isStarted();
        } catch (Exception e) {
            gLogger.error("onStart", e);
            return false;
        }
    }

    @Override
    public void onDestroy() {
        try {


            mBrokerService.stop();
            mBrokerService.waitUntilStopped();
        } catch (Exception e) {
            gLogger.error("onDestroy", e);
        }
    }

    @Override
    public URI getPublishableConnectURI(@NonNull String name) {
        try {
            return mBrokerService
                    .getConnectorByName(name)
                    .getPublishableConnectURI();
        } catch (Exception e) {
            gLogger.error(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public void setName(@NonNull String name) {
        mBrokerService.setBrokerName(name);
    }

    @Override
    public long getTotalConnectionCount() {
        return mBrokerService.getTotalConnections();
    }

    @Override
    public long getConnectionCount(@NonNull String connectorName) {
        TransportConnector connector = mBrokerService.getTransportConnectorByName(connectorName);
        return connector == null ? 0 : connector.connectionCount();
    }

    @Override
    public TransportConnector getTransportConnector(@NonNull String name) {
        return mBrokerService.getTransportConnectorByName(name);
    }

    @Override
    public void dump() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("---BROKER MANSGER DUMP---").append("\n");

        sb.append("Total connections count:")
                .append(getTotalConnectionCount())
                .append("\n")
                .append("including")
                .append("\n")
                .append("openwire:")
                .append(getConnectionCount("openwire"))
                .append("\n")
                .append("mqtt:")
                .append(getConnectionCount("mqtt"))
                .append("\n");

        getTransportConnector("mqtt").getConnections().iterator().forEachRemaining(transportConnection
                -> sb.append("mqtt connection:")
                .append(transportConnection.getConnectionId())
                .append("\n"));

        getTransportConnector("openwire").getConnections().iterator().forEachRemaining(transportConnection
                -> sb.append("openwire connection:")
                .append(transportConnection.getConnectionId())
                .append("\n"));

        sb.append("---BROKER MANSGER DUMP END---");

        gLogger.info(sb.toString());
    }
}

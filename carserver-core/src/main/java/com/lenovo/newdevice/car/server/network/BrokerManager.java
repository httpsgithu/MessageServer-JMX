package com.lenovo.newdevice.car.server.network;

import com.lenovo.newdevice.car.server.application.Component;
import lombok.NonNull;
import org.apache.activemq.broker.TransportConnector;

import java.net.URI;

public interface BrokerManager extends Component {

    URI getPublishableConnectURI(@NonNull String name);

    void setName(@NonNull String name);

    long getTotalConnectionCount();

    long getConnectionCount(@NonNull String connectorName);

    TransportConnector getTransportConnector(@NonNull String name);

    void dump();
}
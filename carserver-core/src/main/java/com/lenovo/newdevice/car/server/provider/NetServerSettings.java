package com.lenovo.newdevice.car.server.provider;

import java.io.IOException;

/**
 * Created @2017/2/27 13:39
 */
public class NetServerSettings extends AbsSettings {

    public NetServerSettings() throws IOException {
        super("net-server-settings.properties");
    }

    public String getBrokerUrl() {
        return get("broker.url", "tcp://127.0.0.1:61616");// OpenWire
    }

    public String getCarListManagerId() {
        return get("broker.car.list.manager.client_id", "CarListManager-1");
    }

    public String getMessageBrokerId() {
        return get("broker.car.message.broker.client_id", "MessageBroker-1");
    }

    public String getMessageBroadcasterId() {
        return get("broker.car.message.broadcaster.client_id", "MessageBroadcaster-1");
    }

    public String getPTPClientIDPropertyName() {
        return get("broker.p2p.client_id_property", "ptp_client_id");
    }

    public String getCarLoginTopic() {
        return get("broker.car.login.topic", "Car-Login");
    }

    public String getCarLogoutTopic() {
        return get("broker.car.logout.topic", "Car-Logout");
    }

    public String getMessageSenderTopic() {
        return get("broker.car.message_sender.topic", "Car-Message-Sender.ptp");
    }

    public String getMessageReceiverTopic() {
        return get("broker.car.message_receiver.topic", "Car-Message-Receiver.ptp");
    }

    public String getMessageBoadcastTopic() {
        return get("broker.car.message_broadcast.topic", "Car-Message-Broadcast.ptp");
    }
}

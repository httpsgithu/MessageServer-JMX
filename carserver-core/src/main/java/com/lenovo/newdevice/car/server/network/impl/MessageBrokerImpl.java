package com.lenovo.newdevice.car.server.network.impl;

import com.lenovo.newdevice.car.server.message.MessageHelper;
import com.lenovo.newdevice.car.server.network.MessageBroker;
import com.lenovo.newdevice.car.server.provider.NetServerSettings;
import com.lenovo.newdevice.carserver.api.model.Car;
import com.lenovo.newdevice.carserver.api.model.PTPMessageContainer;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * Created @2017/2/28 10:56
 */
@Service
public class MessageBrokerImpl extends MQBasedActivity implements MessageBroker {

    @Getter
    @Setter
    private boolean started;

    @Getter
    private MessageProducer producer;

    private Session sharedSession;
    private MessageConsumer consumer1, consumer2;

    public MessageBrokerImpl(NetServerSettings serverSettings) {
        super(serverSettings);
    }


    @Override
    public boolean send(@NonNull Message message, Car target) {
        getLogger().info("Sending msg to:" + target);
        try {
            message.setStringProperty(getServerSettings().getPTPClientIDPropertyName(), target.getUuid());
            getProducer().send(message);
        } catch (JMSException e) {
            getLogger().error(e);
            return false;
        }

        return true;
    }

    @Override
    public boolean onStart() {
        try {
            this.sharedSession = createSession(getServerSettings().getMessageBrokerId());
            this.consumer1 = createConsumer(sharedSession, getServerSettings().getMessageSenderTopic(), this::onIncomingMessage);
            this.producer = createProducer(sharedSession, getServerSettings().getMessageReceiverTopic());
        } catch (JMSException e) {
            getLogger().error(e);
            return false;
        }

        return true;
    }

    private void onIncomingMessage(Message message) {
        String content = MessageHelper.extractContentString((BytesMessage) message);
        getLogger().info(content);
        PTPMessageContainer container = PTPMessageContainer.from(content);
        getLogger().info(container);
        Car target = container.getTarget();
        Message out = new ActiveMQBytesMessage();
        send(out, target);
    }

    @Override
    public void onDestroy() {
        close(producer);
        close(consumer1);
        close(consumer2);
        close(sharedSession);
        closeConnection();
    }

}

package com.lenovo.newdevice.car.server.network.impl;

import com.lenovo.newdevice.car.server.message.MessageHelper;
import com.lenovo.newdevice.car.server.network.MessageSender;
import com.lenovo.newdevice.carserver.api.model.Car;
import com.lenovo.newdevice.carserver.api.model.PTPMessageContainer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * Created @2017/2/28 10:56
 */
@Service
@NoArgsConstructor
public class MessageSenderImpl extends MQBasedActivity implements MessageSender {

    @Getter
    @Setter
    private boolean started;

    @Getter
    private MessageProducer producer;

    private Session sharedSession;
    private MessageConsumer consumer1, consumer2;

    @Override
    public boolean send(@NonNull Message message, Car target) {
        getLogger().info("Sending msg to:" + target);
        try {
            message.setStringProperty(getServerSettings().getPTPClientIDPropertyName(), target.getUuid());
            getProducer().send(message);
        } catch (JMSException e) {
            getLogger().error("send", e);
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
            getLogger().error("onStart", e);
            return false;
        }

        return true;
    }

    private void onIncomingMessage(Message message) {
        String content = MessageHelper.extractContentString((BytesMessage) message);
        PTPMessageContainer container = PTPMessageContainer.from(content);
        getLogger().info(container.toString());
        Car target = container.getTarget();
        send(MessageHelper.constructByteMessage(sharedSession, container.getContent()), target);

        // FIXME
        getBrokerManager().dump();
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

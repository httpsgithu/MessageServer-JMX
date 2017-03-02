package com.lenovo.newdevice.car.server.message;

import com.lenovo.newdevice.carserver.api.model.BytesSerializable;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Created @2017/3/1 15:30
 */
public class MessageHelper {

    private static Logger sLogger = LoggerFactory.getLogger(MessageHelper.class);

    public static String extractContentString(@NonNull BytesMessage bytesMessage) {
        try {
            byte[] data = new byte[(int) bytesMessage.getBodyLength()];
            bytesMessage.readBytes(data);
            return new String(data);
        } catch (JMSException e) {
            sLogger.error("extractContentString", e);
        }
        return null;
    }

    public static BytesMessage createByteMessage(@NonNull Session session, @NonNull BytesSerializable serializable) {
        try {
            BytesMessage bytesMessage = session.createBytesMessage();
            bytesMessage.writeBytes(serializable.toBytes());
            return bytesMessage;
        } catch (JMSException e) {
            sLogger.error("createByteMessage", e);
        }
        return null;
    }


    public static BytesMessage constructByteMessage(@NonNull Session sessions, @NonNull BytesSerializable serializable) {
        try {
            BytesMessage out = sessions.createBytesMessage();
            out.writeBytes(serializable.toBytes());
            return out;
        } catch (JMSException e) {
            sLogger.error("constructByteMessage", e);
        }
        return null;
    }

    public static BytesMessage constructByteMessage(@NonNull Session sessions, @NonNull String src) {
        try {
            BytesMessage out = sessions.createBytesMessage();
            out.writeBytes(src.getBytes());
            return out;
        } catch (JMSException e) {
            sLogger.error("constructByteMessage", e);
        }
        return null;
    }
}

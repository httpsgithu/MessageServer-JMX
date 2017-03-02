package com.lenovo.newdevice.car.server.network;

import com.lenovo.newdevice.car.server.application.Component;
import com.lenovo.newdevice.carserver.api.model.Car;
import lombok.NonNull;

import javax.jms.Message;

/**
 * Created @2017/2/28 10:55
 */
public interface MessageSender extends Component {
    boolean send(@NonNull Message message, Car target);
}

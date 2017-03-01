package com.lenovo.newdevice.car.server.network;

import com.lenovo.newdevice.car.server.application.Component;
import lombok.NonNull;

import javax.jms.Message;

/**
 * Created @2017/2/28 12:07
 */
public interface Broadcaster extends Component {
    boolean sendBroadcast(@NonNull Message message);
}

package com.lenovo.newdevice.car.server.network.impl;

import com.lenovo.newdevice.car.server.message.MessageHelper;
import com.lenovo.newdevice.car.server.network.ClientManager;
import com.lenovo.newdevice.car.server.provider.NetServerSettings;
import com.lenovo.newdevice.carserver.api.model.Car;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created @2017/2/27 14:08
 */
@Service
@NoArgsConstructor
@Setter
public class ClientManagerImpl extends MQBasedActivity implements ClientManager {

    private final List<Car> CARS = new ArrayList<>();

    private Session session;
    private MessageConsumer consumer1, consumer2;

    public ClientManagerImpl(NetServerSettings serverSettings) {
        super(serverSettings);
    }

    @Override
    public boolean onStart() {
        try {
            session = createSession(getServerSettings().getCarListManagerId());
            consumer1 = createConsumer(session, getServerSettings().getCarLoginTopic(), this::onLoginMessage);
            consumer2 = createConsumer(session, getServerSettings().getCarLogoutTopic(), this::onLogoutMessage);
        } catch (JMSException e) {
            getLogger().error(e);
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        close(consumer1);
        close(consumer2);
        close(session);
        closeConnection();
    }

    private void onLoginMessage(Message message) {
        String src = MessageHelper.extractContentString((BytesMessage) message);
        Car car = Car.from(src);
        onRequestLogin(car);
    }

    private void onLogoutMessage(Message message) {
        String src = MessageHelper.extractContentString((BytesMessage) message);
        Car car = Car.from(src);
        onRequestLogout(car);
    }

    private void onRequestLogin(Car car) {
        getLogger().info(car);
        synchronized (CARS) {
            CARS.remove(car);
            CARS.add(car);
        }
    }

    private void onRequestLogout(Car car) {
        getLogger().info(car);
        synchronized (CARS) {
            CARS.remove(car);
        }
    }

    @Override
    public Collection<Car> getCarList() {
        return new ArrayList<>(CARS);
    }

}

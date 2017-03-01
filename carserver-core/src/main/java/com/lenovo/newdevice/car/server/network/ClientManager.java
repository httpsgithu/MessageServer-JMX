package com.lenovo.newdevice.car.server.network;

import com.lenovo.newdevice.car.server.application.Component;
import com.lenovo.newdevice.carserver.api.model.Car;
import lombok.NonNull;

import java.util.Collection;

/**
 * Created @2017/2/27 14:05
 */
public interface ClientManager extends Component {
    @NonNull
    Collection<Car> getCarList();
}

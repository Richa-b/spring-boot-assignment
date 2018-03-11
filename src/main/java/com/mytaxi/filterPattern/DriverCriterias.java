package com.mytaxi.filterPattern;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;

import java.util.Objects;
import java.util.function.Predicate;

public enum DriverCriterias {

    ONLINE_DRIVERS(driverDO ->
            OnlineStatus.ONLINE.equals(driverDO.getOnlineStatus())
    ),
    DRIVERS_WITH_CAR(driverDO ->
            OnlineStatus.ONLINE.equals(driverDO.getOnlineStatus()) && Objects.nonNull(driverDO.getCarDO())),

    DRIVERS_WITH_HYUNDAI_CAR(driverDO ->
            OnlineStatus.ONLINE.equals(driverDO.getOnlineStatus()) && Objects.nonNull(driverDO.getCarDO())
            && driverDO.getCarDO().getManufacturer().getName().equals("Hyundai")),

    DRIVERS_WITH_CONVERTIBLE_CAR(driverDO ->
            OnlineStatus.ONLINE.equals(driverDO.getOnlineStatus()) && Objects.nonNull(driverDO.getCarDO())
            && driverDO.getCarDO().getConvertible());


    private Predicate<DriverDO> filterCondition;

    DriverCriterias(Predicate<DriverDO> driverDOPredicate) {
        this.filterCondition = driverDOPredicate;
    }

    public Predicate<DriverDO> get() {
        return filterCondition;
    }
}

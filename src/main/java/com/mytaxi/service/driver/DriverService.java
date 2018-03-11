package com.mytaxi.service.driver;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.DriverNotOnlineException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.BaseService;

import java.util.List;

public interface DriverService extends BaseService<DriverDO,Long>
{

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);

    CarDO selectCar(long driverId, long carId) throws EntityNotFoundException, DriverNotOnlineException, CarAlreadyInUseException;

    void deselectCar(long driverId) throws EntityNotFoundException;

    List<DriverDO> findAllOnlineDrivers();

    List<DriverDO> findAllDriversWithConvertibleHyundaiCar();

    List<DriverDO> findDriversWithAutomaticElectricCars();

    List<DriverDO> findDriversWithConvertibleHyundaiCars();



}

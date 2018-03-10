package com.mytaxi.service.car;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.BaseService;

public interface CarService extends BaseService<CarDO,Long>{

    CarDO createCar(CarDO carDO) throws ConstraintsViolationException, EntityNotFoundException;

    CarDO findByDriver(DriverDO driverDO);

}

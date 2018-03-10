package com.mytaxi.service.car;


import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.BaseServiceImpl;
import com.mytaxi.service.manufacturer.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class CarServiceImpl extends BaseServiceImpl<CarDO, Long> implements CarService {

    private final CarRepository carRepository;
    private final ManufacturerService manufacturerService;

    @Autowired
    public CarServiceImpl(final CarRepository carRepository, final ManufacturerService manufacturerService) {
        this.carRepository = carRepository;
        this.manufacturerService = manufacturerService;
    }

    @Override
    protected JpaRepository<CarDO, Long> getRepository() {
        return carRepository;
    }


    @Override
    public CarDO createCar(CarDO carDO) throws ConstraintsViolationException, EntityNotFoundException {
        ManufacturerDO manufacturerDO = manufacturerService.find(carDO.getManufacturer().getId());
        carDO.setManufacturer(manufacturerDO);
        return create(carDO);
    }

    @Override
    public CarDO findByDriver(DriverDO driverDO) {
        return carRepository.findByDriverDO(driverDO);
    }
}

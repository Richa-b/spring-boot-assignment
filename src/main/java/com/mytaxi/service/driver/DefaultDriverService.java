package com.mytaxi.service.driver;

import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.DriverNotOnlineException;
import com.mytaxi.exception.EntityNotFoundException;

import java.util.List;
import java.util.Objects;

import com.mytaxi.filterPattern.AndCriteria;
import com.mytaxi.filterPattern.Criteria;
import com.mytaxi.filterPattern.CriteriaWithConditon;
import com.mytaxi.filterPattern.DriverCriterias;
import com.mytaxi.service.car.CarService;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;

    private final CarService carService;

    public DefaultDriverService(final DriverRepository driverRepository, final CarService carService) {
        this.driverRepository = driverRepository;
        this.carService = carService;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    @Override
    @Transactional
    public CarDO selectCar(long driverId, long carId) throws EntityNotFoundException, DriverNotOnlineException, CarAlreadyInUseException {
        DriverDO driverDO = findDriverChecked(driverId);
        CarDO carDO = carService.find(carId);
        if (!OnlineStatus.ONLINE.equals(driverDO.getOnlineStatus())) {
            throw new DriverNotOnlineException("Driver with id:" + driverDO.getId() + " is not ONLINE");
        } else {
            DriverDO someOtherDriver = carDO.getDriverDO();
            if (Objects.nonNull(someOtherDriver) && !someOtherDriver.getId().equals(driverDO.getId())) {
                throw new CarAlreadyInUseException("Requested Car with id:" + carId + " is already in use by some other driver");
            }
            carDO.setDriverDO(driverDO);
        }
        return carDO;
    }

    @Override
    @Transactional
    public void deselectCar(long driverId) throws EntityNotFoundException {
        DriverDO driverDO = findDriverChecked(driverId);
        CarDO carDO = carService.findByDriver(driverDO);
        if (Objects.nonNull(carDO)) {
            carDO.setDriverDO(null);
        }
    }

    @Override
    public List<DriverDO> findAllOnlineDrivers() {
        Criteria onlineDriversCriteria = new CriteriaWithConditon<DriverDO>(DriverCriterias.ONLINE_DRIVERS.get());
        return onlineDriversCriteria.satisfy(((List<DriverDO>) driverRepository.findAll()));
    }

    @Override
    public List<DriverDO> findAllDriversWithConvertibleHyundaiCar() {
        Criteria hyundaiCarCriteria = new CriteriaWithConditon<DriverDO>(DriverCriterias.DRIVERS_WITH_HYUNDAI_CAR.get());
        Criteria convertibleCarCriteria = new CriteriaWithConditon<DriverDO>(DriverCriterias.DRIVERS_WITH_CONVERTIBLE_CAR.get());
        Criteria andCritera = new AndCriteria(hyundaiCarCriteria, convertibleCarCriteria);
        return andCritera.satisfy(((List<DriverDO>) driverRepository.findAll()));
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException {

        DriverDO driverDO = driverRepository.findOne(driverId);
        if (driverDO == null)
        {
            throw new EntityNotFoundException("Could not find entity with id: " + driverId);
        }
        return driverDO;
    }

}

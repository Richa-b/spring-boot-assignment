package com.mytaxi.controller;

import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.DriverNotOnlineException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.driver.DriverService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController
{

    private final DriverService driverService;


    @Autowired
    public DriverController(final DriverService driverService)
    {
        this.driverService = driverService;
    }


    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(
        @Valid @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }

    @PutMapping("selectCar/{driverId}")
    public CarDTO selectCar(@PathVariable long driverId, @RequestParam Long carId ) throws EntityNotFoundException, CarAlreadyInUseException, DriverNotOnlineException {
        return CarMapper.makeCarDTO(driverService.selectCar(driverId,carId));
    }

    @PutMapping("deselectCar/{driverId}")
    public void deselectCar(@PathVariable long driverId) throws EntityNotFoundException{
        driverService.deselectCar(driverId);
    }

    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }

    // Following are the examples using Filter Pattern
    // Though following should not be used, instead Hibernate criteria should be used.
    // Hibernate examples added below
    @GetMapping("filter/online")
    public List<DriverDTO> findOnlineDrivers()
    {
        return DriverMapper.makeDriverDTOList(driverService.findAllOnlineDrivers());
    }

    @GetMapping("filter/hyundaiConvertible")
    public List<DriverDTO> findAllDriversWithConvertibleHyundaiCar(){
        return DriverMapper.makeDriverDTOList(driverService.findAllDriversWithConvertibleHyundaiCar());
    }


    //Following are the examples for fetching Drivers  Using Hibernate Criteria

    @GetMapping("criteria/automaticElectric")
    public List<DriverDTO> findDriversWithAutomaticElectricCars()
    {
        return DriverMapper.makeDriverDTOList(driverService.findDriversWithAutomaticElectricCars());
    }

    @GetMapping("criteria/hyundaiConvertible")
    public List<DriverDTO> findDriversWithConvertibleHyundaiCars()
    {
        return DriverMapper.makeDriverDTOList(driverService.findDriversWithConvertibleHyundaiCars());
    }
}

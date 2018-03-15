package com.mytaxi.unit.service;

import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.*;
import com.mytaxi.service.car.CarService;
import com.mytaxi.service.driver.DefaultDriverService;
import com.mytaxi.service.driver.DriverService;
import com.mytaxi.unit.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class DriverServiceTest {

    private DriverRepository driverRepository;

    private DriverService driverService;

    private CarService carService;

    @Before
    public void setup() {
        driverRepository = Mockito.mock(DriverRepository.class);
        carService = Mockito.mock(CarService.class);
        driverService = new DefaultDriverService(driverRepository, carService);
    }


    @Test
    public void findById() throws EntityNotFoundException {
        DriverDO defaultDriverDO = TestUtils.getTestDriverDO(1).get(0);
        Mockito.when(driverRepository.findOne(anyLong())).thenReturn(defaultDriverDO);
        String driver = "Driver1";
        DriverDO driverDO = driverService.find(1l);
        assertEquals(driverDO.getUsername(), driver);
        // verify that repository method was called once
        verify(driverRepository).findOne(anyLong());

    }

    @Test(expected = EntityNotFoundException.class)
    public void findDriverNotExist() throws EntityNotFoundException {
        Mockito.when(driverRepository.findOne(anyLong())).thenReturn(null);
        driverService.find(1l);
    }

    @Test
    public void create() throws EntityNotFoundException, ConstraintsViolationException {
        DriverDO defaultDriverDO = TestUtils.getTestDriverDO(1).get(0);
        Mockito.when(driverRepository.save(any(DriverDO.class))).thenReturn(defaultDriverDO);
        String driver = "Driver1";
        DriverDO driverDO = driverService.create(defaultDriverDO);
        assertEquals(driverDO.getUsername(), driver);
        // verify that repository method was called once
        verify(driverRepository).save(any(DriverDO.class));

    }


    @Test(expected = ConstraintsViolationException.class)
    public void createConstraintsException() throws EntityNotFoundException, ConstraintsViolationException {
        DriverDO defaultDriverDO = TestUtils.getTestDriverDO(1).get(0);
        Mockito.doThrow(new DataIntegrityViolationException("")).when(driverRepository).save(any(DriverDO.class));
        driverService.create(defaultDriverDO);

    }


    @Test
    public void deleteById() throws EntityNotFoundException {
        DriverDO defaultDriverDO = TestUtils.getTestDriverDO(1).get(0);
        Mockito.when(driverRepository.findOne(anyLong())).thenReturn(defaultDriverDO);
        driverService.delete(1l);
        assertEquals(true, defaultDriverDO.getDeleted());
        // verify that repository method was called once
        verify(driverRepository).findOne(anyLong());

    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteDriverNotExist() throws EntityNotFoundException {
        Mockito.when(driverRepository.findOne(anyLong())).thenReturn(null);
        driverService.delete(1l);
    }



    @Test
    public void updateLocation() throws EntityNotFoundException {
        DriverDO defaultDriverDO = TestUtils.getTestDriverDO(1).get(0);
        Mockito.when(driverRepository.findOne(anyLong())).thenReturn(defaultDriverDO);
        driverService.updateLocation(1l,80,80);
        assertEquals(80, defaultDriverDO.getCoordinate().getLatitude(),0.001);
        assertEquals(80, defaultDriverDO.getCoordinate().getLatitude(),0.001);
        // verify that repository method was called once
        verify(driverRepository).findOne(anyLong());

    }

    @Test(expected = EntityNotFoundException.class)
    public void updateLocationException() throws EntityNotFoundException {
        Mockito.when(driverRepository.findOne(anyLong())).thenReturn(null);
        driverService.updateLocation(1l,80,80);
    }


    @Test
    public void findDriversByStatus() {
        List<DriverDO> defaultDriverDOList = TestUtils.getTestDriverDO(2);
        Mockito.when(driverRepository.findByOnlineStatus(any(OnlineStatus.class))).thenReturn(defaultDriverDOList);
        List<DriverDO> driverList = driverService.find(OnlineStatus.ONLINE);
        assertEquals(driverList.size(), defaultDriverDOList.size());
        // verify that repository method was called once
        verify(driverRepository).findByOnlineStatus(any(OnlineStatus.class));

    }

    @Test
    public void selectCar() throws EntityNotFoundException, CarAlreadyInUseException, DriverNotOnlineException, CarSelectDeselectException {
        DriverDO defaultDriverDO = TestUtils.getTestDriverDO(1).get(0);
        defaultDriverDO.setOnlineStatus(OnlineStatus.ONLINE);
        CarDO defaultCarDO = TestUtils.getTestCarDO(1).get(0);
        Mockito.when(driverRepository.findOne(anyLong())).thenReturn(defaultDriverDO);
        Mockito.when(carService.find(anyLong())).thenReturn(defaultCarDO);
        CarDO carDO = driverService.selectCar(1l,1l);

        assertNotNull(carDO);
        assertEquals(carDO.getDriverDO().getUsername(),defaultDriverDO.getUsername());
        verify(driverRepository).findOne(anyLong());
        verify(carService).find(anyLong());
    }

    @Test(expected = DriverNotOnlineException.class)
    public void selectCarWhenNotOnline() throws EntityNotFoundException, CarAlreadyInUseException, DriverNotOnlineException, CarSelectDeselectException {
        DriverDO defaultDriverDO = TestUtils.getTestDriverDO(1).get(0);
        CarDO defaultCarDO = TestUtils.getTestCarDO(1).get(0);
        Mockito.when(driverRepository.findOne(anyLong())).thenReturn(defaultDriverDO);
        Mockito.when(carService.find(anyLong())).thenReturn(defaultCarDO);
        CarDO carDO = driverService.selectCar(1l,1l);

        assertNull(carDO);
    }

    @Test(expected = CarAlreadyInUseException.class)
    public void selectCarWhenAlreadyAllotted() throws EntityNotFoundException, CarAlreadyInUseException, DriverNotOnlineException, CarSelectDeselectException {
        List<DriverDO> defaultDriverDOList = TestUtils.getTestDriverDO(2);
        DriverDO defaultDriverDO = defaultDriverDOList.get(0);
        defaultDriverDO.setOnlineStatus(OnlineStatus.ONLINE);
        CarDO defaultCarDO = TestUtils.getTestCarDO(1).get(0);
        DriverDO someDriver = defaultDriverDOList.get(1);
        someDriver.setId(2l);
        defaultCarDO.setDriverDO(someDriver);
        Mockito.when(driverRepository.findOne(anyLong())).thenReturn(defaultDriverDO);
        Mockito.when(carService.find(anyLong())).thenReturn(defaultCarDO);
        CarDO carDO = driverService.selectCar(1l,1l);

        assertNull(carDO);
    }


    @Test
    public void deselectCar() throws EntityNotFoundException, CarSelectDeselectException {
        DriverDO defaultDriverDO = TestUtils.getTestDriverDO(1).get(0);
        CarDO defaultCarDO = TestUtils.getTestCarDO(1).get(0);
        defaultCarDO.setDriverDO(defaultDriverDO);
        Mockito.when(driverRepository.findOne(anyLong())).thenReturn(defaultDriverDO);
        Mockito.when(carService.findByDriver(any(DriverDO.class))).thenReturn(defaultCarDO);
        driverService.deselectCar(1l);

        assertNull(defaultCarDO.getDriverDO());
        verify(driverRepository).findOne(anyLong());
        verify(carService).findByDriver(any(DriverDO.class));
    }

    @Test(expected = EntityNotFoundException.class)
    public void deselectCarWhenDriverNotFound() throws EntityNotFoundException, CarSelectDeselectException {
        CarDO defaultCarDO = TestUtils.getTestCarDO(1).get(0);
        Mockito.when(driverRepository.findOne(anyLong())).thenReturn(null);
        Mockito.when(carService.findByDriver(any(DriverDO.class))).thenReturn(defaultCarDO);
        driverService.deselectCar(1l);

        verify(driverRepository).findOne(anyLong());
        verify(carService).findByDriver(any(DriverDO.class));
    }


    @Test
    public void findAllOnlineDrivers() {
        List<DriverDO> defaultDriverDOList = TestUtils.getTestDriverDO(3);
        defaultDriverDOList.get(0).setOnlineStatus(OnlineStatus.ONLINE);
        defaultDriverDOList.get(2).setOnlineStatus(OnlineStatus.ONLINE);
        Mockito.when(driverRepository.findAll()).thenReturn(defaultDriverDOList);
        List<DriverDO> driversFetched = driverService.findAllOnlineDrivers();

        assertEquals(2,driversFetched.size());
        verify(driverRepository).findAll();
    }

    @Test
    public void findAllDriversWithConvertibleHyundaiCar() {
        List<DriverDO> defaultDriverDOList = TestUtils.getTestDriverDO(3);
        DriverDO driverDO = defaultDriverDOList.get(0);
        driverDO.setOnlineStatus(OnlineStatus.ONLINE);
        CarDO carDO = new CarDO();
        ManufacturerDO manufacturerDO = new ManufacturerDO();
        manufacturerDO.setName("Hyundai");
        carDO.setManufacturer(manufacturerDO);
        carDO.setConvertible(true);
        driverDO.setCarDO(carDO);
        Mockito.when(driverRepository.findAll()).thenReturn(defaultDriverDOList);
        List<DriverDO> driversFetched = driverService.findAllDriversWithConvertibleHyundaiCar();

        assertEquals(1,driversFetched.size());
        verify(driverRepository).findAll();
    }

}

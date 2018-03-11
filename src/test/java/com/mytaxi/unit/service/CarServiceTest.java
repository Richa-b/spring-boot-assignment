package com.mytaxi.unit.service;


import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.CarService;
import com.mytaxi.service.car.CarServiceImpl;
import com.mytaxi.service.manufacturer.ManufacturerService;
import com.mytaxi.unit.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class CarServiceTest {

    private CarRepository carRepository;

    private ManufacturerService manufacturerService;

    private CarService carService;

    @Before
    public void setup() {
        carRepository = Mockito.mock(CarRepository.class);
        manufacturerService = Mockito.mock(ManufacturerService.class);
        carService = new CarServiceImpl(carRepository, manufacturerService);
    }

    @Test
    public void findCarByDriver() {
        CarDO defaultCarDO = TestUtils.getTestCarDO(1).get(0);
        Mockito.when(carRepository.findByDriverDO(any(DriverDO.class))).thenReturn(defaultCarDO);
        String model = "Test Model1";
        CarDO carDO = carService.findByDriver(new DriverDO("Test Driver", "abc"));
        assertEquals(carDO.getModel(), model);
        // verify that repository method was called once
        verify(carRepository).findByDriverDO(any(DriverDO.class));

    }

    @Test
    public void findCarByDriverNotExist() {
        Mockito.when(carRepository.findByDriverDO(any(DriverDO.class))).thenReturn(null);
        CarDO carDO = carService.findByDriver(new DriverDO("Test Driver", "abc"));
        assertNull(carDO);
        // verify that repository method was called once
        verify(carRepository).findByDriverDO(any(DriverDO.class));

    }


    @Test
    public void findById() throws EntityNotFoundException {
        CarDO defaultCarDO = TestUtils.getTestCarDO(1).get(0);
        Mockito.when(carRepository.findOne(anyLong())).thenReturn(defaultCarDO);
        String model = "Test Model1";
        CarDO carDO = carService.find(1l);
        assertEquals(carDO.getModel(), model);
        // verify that repository method was called once
        verify(carRepository).findOne(anyLong());

    }

    @Test(expected = EntityNotFoundException.class)
    public void findCarNotExist() throws EntityNotFoundException {
        Mockito.when(carRepository.findOne(anyLong())).thenReturn(null);
        carService.find(1l);
    }

    @Test
    public void create() throws EntityNotFoundException, ConstraintsViolationException {
        CarDO defaultCarDO = TestUtils.getTestCarDO(1).get(0);
        Mockito.when(carRepository.save(any(CarDO.class))).thenReturn(defaultCarDO);
        Mockito.when(manufacturerService.find(anyLong())).thenReturn(new ManufacturerDO());
        String model = "Test Model1";
        CarDO carDO = carService.createCar(defaultCarDO);
        assertEquals(carDO.getModel(), model);
        // verify that repository method was called once
        verify(carRepository).save(any(CarDO.class));
        verify(manufacturerService).find(anyLong());

    }


    @Test(expected = ConstraintsViolationException.class)
    public void createConstraintsException() throws EntityNotFoundException, ConstraintsViolationException {
        CarDO defaultCarDO = TestUtils.getTestCarDO(1).get(0);
        Mockito.when(manufacturerService.find(anyLong())).thenReturn(new ManufacturerDO());
        Mockito.doThrow(new DataIntegrityViolationException("")).when(carRepository).save(any(CarDO.class));
        carService.createCar(defaultCarDO);

    }


    @Test
    public void deleteById() throws EntityNotFoundException {
        CarDO defaultCarDO = TestUtils.getTestCarDO(1).get(0);
        Mockito.when(carRepository.findOne(anyLong())).thenReturn(defaultCarDO);
        carService.delete(1l);
        assertEquals(true, defaultCarDO.getDeleted());
        // verify that repository method was called once
        verify(carRepository).findOne(anyLong());

    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteCarNotExist() throws EntityNotFoundException {
        Mockito.when(carRepository.findOne(anyLong())).thenReturn(null);
        carService.delete(1l);
    }
}

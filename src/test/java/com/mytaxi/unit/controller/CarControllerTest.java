package com.mytaxi.unit.controller;


import com.mytaxi.controller.CarController;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.CarService;
import com.mytaxi.unit.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
@WithMockUser
public class CarControllerTest {

    @MockBean
    CarService carService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getCarExist() throws Exception {
        CarDO carDO = TestUtils.getTestCarDO(1).get(0);
        Mockito.when(carService.find(Mockito.anyLong())).thenReturn(carDO);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/cars/" + 1l)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        // verify that service method was called once
        verify(carService).find(any(Long.class));

        CarDTO carDTO = TestUtils.jsonToObject(result.getResponse()
                .getContentAsString(), CarDTO.class);
        assertNotNull(carDTO);
        assertEquals("Test Model1", carDTO.getModel());
    }

    @Test
    public void getCarNotExist() throws Exception {

        Mockito.when(carService.find(Mockito.anyLong())).thenThrow(new EntityNotFoundException("Entity Not Found"));
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/cars/" + 1l)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status);
        // verify that service method was called once
        verify(carService).find(any(Long.class));

        assertEquals(result.getResponse().getContentAsString(), "");
    }

    @Test
    public void createCar() throws Exception {
        CarDO carDO =  TestUtils.getTestCarDO(1).get(0);
        String carDTOJsonString = "{\"carType\":\"SMALL\",\"color\":\"White\",\"convertible\":true,\"engineType\":\"ELECTRIC\",\"licensePlate\":\"MH-15-AB-1234\",\"manufacturerId\":1,\"model\":\"Test Model\",\"rating\":0,\"seatCount\":0,\"transmission\":\"MANUAL\"}";
        Mockito.when(carService.createCar(Mockito.any(CarDO.class))).thenReturn(carDO);
        MvcResult result = mockMvc.perform(
                post("/v1/cars")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(carDTOJsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(),status);

        // verify that service method was called once
        verify(carService).createCar(any(CarDO.class));

        CarDTO carDTO = TestUtils.jsonToObject(result.getResponse()
                .getContentAsString(), CarDTO.class);
        assertNotNull(carDTO);
        assertEquals("Test Model1", carDTO.getModel());
    }


    @Test
    public void createCarConstraintsException() throws Exception {

        String carDTOJsonString = "{\"carType\":\"SMALL\",\"color\":\"White\",\"convertible\":true,\"engineType\":\"ELECTRIC\",\"licensePlate\":\"MH-15-AB-1234\",\"manufacturerId\":1,\"model\":\"Test Model\",\"rating\":0,\"seatCount\":0,\"transmission\":\"MANUAL\"}";
        Mockito.when(carService.createCar(Mockito.any(CarDO.class))).thenThrow(new ConstraintsViolationException("Some Constraints Violated"));
        MvcResult result = mockMvc.perform(
                post("/v1/cars")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(carDTOJsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(),status);

        // verify that service method was called once
        verify(carService).createCar(any(CarDO.class));

        assertEquals(result.getResponse()
                .getContentAsString(), "");
    }


    @Test
    public void findAll() throws Exception {

        List<CarDO> carDOList = TestUtils.getTestCarDO(2);
        Mockito.when(carService.findAll()).thenReturn(carDOList);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/cars/")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        // verify that service method was called once
        verify(carService).findAll();

        List<CarDTO> carDTOList = TestUtils.jsonToObject(result.getResponse()
                .getContentAsString(), List.class);
        assertNotNull(carDOList);
        assertEquals(2, carDTOList.size());
    }


    @Test
    public void deleteCar() throws Exception {

        Mockito.doNothing().when(carService).delete(anyLong());

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.delete("/v1/cars/" + 1l)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        // verify that service method was called once
        verify(carService).delete(any(Long.class));

    }

    @Test
    public void deleteCarNotExist() throws Exception {

        Mockito.doThrow(new EntityNotFoundException("Entity Not found to be deleted")).when(carService).delete(anyLong());

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.delete("/v1/cars/" + 1l)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status);

        // verify that service method was called once
        verify(carService).delete(any(Long.class));

    }

    @Test
    @WithMockUser(roles = {"DRIVER"})
    public void deleteCarNotExistSecuredTest() throws Exception {

        Mockito.doThrow(new EntityNotFoundException("Entity Not found to be deleted")).when(carService).delete(anyLong());

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.delete("/v1/cars/" + 1l)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.FORBIDDEN.value(), status);

        // verify that service method was not called
        verify(carService,times(0)).delete(any(Long.class));

    }
}

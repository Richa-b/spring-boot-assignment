package com.mytaxi.unit.controller;


import com.mytaxi.controller.DriverController;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.driver.DriverService;
import com.mytaxi.unit.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(DriverController.class)
public class DriverControllerTest {

    @MockBean
    DriverService driverService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getDriverExist() throws Exception {
        DriverDO driverDO = TestUtils.getTestDriverDO(1).get(0);
        Mockito.when(driverService.find(Mockito.anyLong())).thenReturn(driverDO);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/drivers/" + 1l)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        // verify that service method was called once
        verify(driverService).find(any(Long.class));

        DriverDTO driverDTO = TestUtils.jsonToObject(result.getResponse()
                .getContentAsString(), DriverDTO.class);
        assertNotNull(driverDTO);
        assertEquals("Driver1", driverDTO.getUsername());
    }

    @Test
    public void getDriverNotExist() throws Exception {

        Mockito.when(driverService.find(Mockito.anyLong())).thenThrow(new EntityNotFoundException("Entity Not Found"));
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/drivers/" + 1l)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status);
        // verify that service method was called once
        verify(driverService).find(any(Long.class));

        assertEquals(result.getResponse().getContentAsString(), "");
    }

    @Test
    public void createDriver() throws Exception {
        DriverDO driverDO = TestUtils.getTestDriverDO(1).get(0);
        String driverDTOJsonString = "{\"coordinate\":{\"latitude\":0,\"longitude\":0},\"id\":0,\"password\":\"abcdef\",\"username\":\"Driver1\"}";
        Mockito.when(driverService.create(Mockito.any(DriverDO.class))).thenReturn(driverDO);
        MvcResult result = mockMvc.perform(
                post("/v1/drivers")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(driverDTOJsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(), status);

        // verify that service method was called once
        verify(driverService).create(any(DriverDO.class));

        DriverDTO driverDTO = TestUtils.jsonToObject(result.getResponse()
                .getContentAsString(), DriverDTO.class);
        assertNotNull(driverDTO);
        assertEquals("Driver1", driverDTO.getUsername());
    }


    @Test
    public void createDriverConstraintsException() throws Exception {

        String driverDTOJsonString = "{\"coordinate\":{\"latitude\":0,\"longitude\":0},\"id\":0,\"password\":\"abcdef\",\"username\":\"Driver1\"}";
        Mockito.when(driverService.create(Mockito.any(DriverDO.class))).thenThrow(new ConstraintsViolationException("Some Constraints Violated"));
        MvcResult result = mockMvc.perform(
                post("/v1/drivers")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(driverDTOJsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status);

        // verify that service method was called once
        verify(driverService).create(any(DriverDO.class));

        assertEquals(result.getResponse()
                .getContentAsString(), "");
    }


    @Test
    public void deleteDriver() throws Exception {

        Mockito.doNothing().when(driverService).delete(anyLong());

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.delete("/v1/drivers/" + 1l)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        // verify that service method was called once
        verify(driverService).delete(any(Long.class));

    }

    @Test
    public void deleteDriverNotExist() throws Exception {

        Mockito.doThrow(new EntityNotFoundException("Entity Not found to be deleted")).when(driverService).delete(anyLong());

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.delete("/v1/drivers/" + 1l)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status);

        // verify that service method was called once
        verify(driverService).delete(any(Long.class));

    }

    @Test
    public void updateLocation() throws Exception {

        Mockito.doNothing().when(driverService).updateLocation(any(Long.class),anyDouble(),anyDouble());
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put("/v1/drivers/" + 1l)
                        .param("latitude","80.0").param("longitude","80")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        // verify that service method was called once
        verify(driverService).updateLocation(any(Long.class),anyDouble(),anyDouble());

    }


    @Test
    public void selectCar() throws Exception {

        CarDO carDO = TestUtils.getTestCarDO(1).get(0);
        Mockito.when(driverService.selectCar(anyLong(),anyLong())).thenReturn(carDO);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put("/v1/drivers/selectCar/" + 1l)
                        .param("carId","1")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        // verify that service method was called once
        verify(driverService).selectCar(anyLong(),anyLong());

        CarDTO carDTO = TestUtils.jsonToObject(result.getResponse()
                .getContentAsString(), CarDTO.class);
        assertNotNull(carDTO);
        assertEquals("Test Model1", carDTO.getModel());

    }


    @Test
    public void selectCarWhenException() throws Exception {

        Mockito.doThrow(new CarAlreadyInUseException("Car Already In Use")).when(driverService).selectCar(anyLong(),anyLong());
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put("/v1/drivers/selectCar/" + 1l)
                        .param("carId","1")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status);
        // verify that service method was called once
        verify(driverService).selectCar(anyLong(),anyLong());
        assertEquals(result.getResponse()
                .getContentAsString(), "");

    }


    @Test
    public void deselectCar() throws Exception {

        Mockito.doNothing().when(driverService).deselectCar(anyLong());
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put("/v1/drivers/deselectCar/" + 1l)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        // verify that service method was called once
        verify(driverService).deselectCar(anyLong());
    }


    @Test
    public void deselectCarWhenException() throws Exception {

        Mockito.doThrow(new EntityNotFoundException("Entity Not found")).when(driverService).deselectCar(anyLong());
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put("/v1/drivers/deselectCar/" + 1l)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status);
        // verify that service method was called once
        verify(driverService).deselectCar(anyLong());
        assertEquals(result.getResponse()
                .getContentAsString(), "");

    }


}

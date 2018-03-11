package com.mytaxi.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.CarType;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.Transmission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public  static <T> T jsonToObject(String json, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }


    public static  <T> String objectToJson(T object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }


    public static List<CarDO> getTestCarDO(Integer count){
        List<CarDO> carDOList = new ArrayList<>();
        for(int i=1 ; i <= count; i++) {
            carDOList.add(new CarDO("Test Model" + i, "White", "MH-15-AB-1234", 5, true, 4f, EngineType.ELECTRIC, Transmission.MANUAL, CarType.LUXURY,
                    1l));
        }
        return carDOList;
    }


    public static List<DriverDO> getTestDriverDO(Integer count){
        List<DriverDO> driverDOS = new ArrayList<>();
        for(int i=1 ; i <= count; i++) {
            driverDOS.add(new DriverDO("Driver" + i,"test_password"));
        }
        return driverDOS;
    }
}

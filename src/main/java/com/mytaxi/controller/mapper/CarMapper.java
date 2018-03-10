package com.mytaxi.controller.mapper;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CarMapper {

    public static CarDO makeCarDO(CarDTO carDTO) {
        return new CarDO(carDTO.getModel(), carDTO.getColor(), carDTO.getLicensePlate(),
                carDTO.getSeatCount(), carDTO.getConvertible(), carDTO.getRating(), carDTO.getEngineType(),
                carDTO.getTransmission(), carDTO.getCarType(), carDTO.getManufacturerId());
    }


    public static CarDTO makeCarDTO(CarDO carDO) {
        CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder()
                .setId(carDO.getId())
                .setColor(carDO.getColor())
                .setCarType(carDO.getCarType())
                .setModel(carDO.getModel())
                .setConvertible(carDO.getConvertible())
                .setRating(carDO.getRating())
                .setLicensePlate(carDO.getLicensePlate())
                .setSeatCount(carDO.getSeatCount())
                .setEngineType(carDO.getEngineType())
                .setTransmission(carDO.getTransmission())
                .setManufacturer(carDO.getManufacturer().getName());
        return carDTOBuilder.createCarDTO();
    }


    public static List<CarDTO> makeCarDTOList(Collection<CarDO> cars) {
        return cars.stream()
                .map(CarMapper::makeCarDTO)
                .collect(Collectors.toList());
    }
}

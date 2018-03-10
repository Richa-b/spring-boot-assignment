package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mytaxi.domainvalue.CarType;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.Transmission;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CarDTO {
    @JsonIgnore
    private Long id;

    @NotNull(message = "Model can not be null!")
    private String model;

    private String color;

    @NotNull(message = "License Number can not be null!")
    private String licensePlate;

    private Integer seatCount;

    private Boolean convertible;

    private Float rating;

    @NotNull(message = "Engine Type can not be null!")
    private EngineType engineType;

    private Transmission transmission;

    private CarType carType;

    private String manufacturer;
    private Long manufacturerId;

    private CarDTO() {
    }

    private CarDTO(Long id, String model, String color, String licensePlate, Integer seatCount, Boolean convertible, Float rating, EngineType engineType, Transmission transmission, CarType carType, String manufacturer) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.transmission = transmission;
        this.carType = carType;
        this.manufacturer = manufacturer;
    }


    public static CarDTO.CarDTOBuilder newBuilder() {
        return new CarDTO.CarDTOBuilder();
    }

    @Getter
    public static class CarDTOBuilder {
        private Long id;
        private String model;
        private String color;
        private String licensePlate;
        private Integer seatCount;
        private Boolean convertible;
        private Float rating;
        private EngineType engineType;
        private Transmission transmission;
        private CarType carType;
        private String manufacturer;


        public CarDTO.CarDTOBuilder setId(Long id) {
            this.id = id;
            return this;
        }


        public CarDTO.CarDTOBuilder setModel(String model) {
            this.model = model;
            return this;
        }

        public CarDTO.CarDTOBuilder setColor(String color) {
            this.color = color;
            return this;
        }


        public CarDTO createCarDTO() {
            return new CarDTO(id, model, color, licensePlate,seatCount,convertible,rating,engineType,transmission,carType,manufacturer);
        }

        public CarDTO.CarDTOBuilder setSeatCount(Integer seatCount) {
            this.seatCount = seatCount;
            return this;
        }

        public CarDTO.CarDTOBuilder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public CarDTO.CarDTOBuilder setConvertible(Boolean convertible) {
            this.convertible = convertible;
            return this;
        }
        public CarDTO.CarDTOBuilder setRating(Float rating) {
            this.rating = rating;
            return this;
        }
        public CarDTO.CarDTOBuilder setEngineType(EngineType engineType) {
            this.engineType = engineType;
            return this;
        }
        public CarDTO.CarDTOBuilder setTransmission(Transmission transmission) {
            this.transmission = transmission;
            return this;
        }

        public CarDTO.CarDTOBuilder setCarType(CarType carType) {
            this.carType = carType;
            return this;
        }

        public CarDTO.CarDTOBuilder setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }
    }
}


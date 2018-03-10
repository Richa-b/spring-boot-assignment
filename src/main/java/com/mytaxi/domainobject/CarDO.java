package com.mytaxi.domainobject;


import com.mytaxi.domainvalue.CarType;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.Transmission;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "car")
@Getter
@Setter
public class CarDO extends BaseDO {


    @Column(nullable = false)
    @NotNull(message = "Name can not be null!")
    private String model;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String licensePlate;

    @Column(nullable = false)
    private Integer seatCount;

    @Column(nullable = false)
    private Boolean convertible;

    private Float rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EngineType engineType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Transmission transmission;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarType carType;

    @ManyToOne
    ManufacturerDO manufacturer;

    public CarDO(String model, String color, String licensePlate, Integer seatCount, Boolean convertible, Float rating, EngineType engineType, Transmission transmission, CarType carType,Long manufacturerId) {
        this.model = model;
        this.color = color;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.transmission = transmission;
        this.carType = carType;
        this.manufacturer = new ManufacturerDO(manufacturerId);
    }

    public CarDO() {
    }
}

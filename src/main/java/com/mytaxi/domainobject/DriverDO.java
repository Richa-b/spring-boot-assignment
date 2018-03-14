package com.mytaxi.domainobject;

import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "driver")
@Getter
@Setter
public class DriverDO extends UserDO {

    @Embedded
    private GeoCoordinate coordinate;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCoordinateUpdated = ZonedDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OnlineStatus onlineStatus;

    @OneToOne(mappedBy = "driverDO")
    private CarDO carDO;

    private DriverDO() {
    }


    public DriverDO(String username, String password) {
        this.username = username;
        this.password = password;
        this.coordinate = null;
        this.dateCoordinateUpdated = null;
        this.onlineStatus = OnlineStatus.OFFLINE;
        this.setDeleted(false);
    }

    public void setCoordinate(GeoCoordinate coordinate) {
        this.coordinate = coordinate;
        this.dateCoordinateUpdated = ZonedDateTime.now();
    }
}

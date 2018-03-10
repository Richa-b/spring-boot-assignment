package com.mytaxi.domainobject;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "manufacturer")
@Getter
@Setter
public class ManufacturerDO extends BaseDO {

    @Column(nullable = false)
    @NotNull(message = "Manufacturer name can not be null!")
    private String name;

    //Some Other properties

    public ManufacturerDO(Long id) {
        this.setId(id);
    }

    public ManufacturerDO() {
    }

}

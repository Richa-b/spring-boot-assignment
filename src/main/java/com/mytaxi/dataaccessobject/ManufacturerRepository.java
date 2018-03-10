package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.ManufacturerDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<ManufacturerDO, Long> {

}

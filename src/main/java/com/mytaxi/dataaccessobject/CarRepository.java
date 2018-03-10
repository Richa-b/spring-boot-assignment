package com.mytaxi.dataaccessobject;


import com.mytaxi.domainobject.CarDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Database Access Object for car table.
 * <p/>
 */
@Repository
public interface CarRepository extends JpaRepository<CarDO, Long> {

}

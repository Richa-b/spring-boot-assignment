package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDO, Long> {
    UserDO findByUsername(String userName);
}

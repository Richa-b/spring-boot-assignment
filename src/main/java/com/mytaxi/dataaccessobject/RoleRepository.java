package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleDO, Long> {
}

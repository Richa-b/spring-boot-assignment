package com.mytaxi.service.security;

import com.mytaxi.dataaccessobject.RoleRepository;
import com.mytaxi.domainobject.RoleDO;
import com.mytaxi.service.BaseServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleDO, Long> implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    protected JpaRepository<RoleDO, Long> getRepository() {
        return roleRepository;
    }

}

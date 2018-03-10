package com.mytaxi.service.manufacturer;

import com.mytaxi.dataaccessobject.ManufacturerRepository;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerServiceImpl extends BaseServiceImpl<ManufacturerDO, Long> implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerServiceImpl(final ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }


    @Override
    protected JpaRepository<ManufacturerDO, Long> getRepository() {
        return manufacturerRepository;
    }
}

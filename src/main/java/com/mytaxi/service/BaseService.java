package com.mytaxi.service;

import com.mytaxi.domainobject.BaseDO;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.ConstraintsViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


import java.io.Serializable;
import java.util.List;

/**
 * Base Interface For that define Common CRUD operations
 * <p/>
 */
public interface BaseService<T extends BaseDO, ID extends Serializable> {

    T find(ID id) throws EntityNotFoundException;

    T create(T entity) throws ConstraintsViolationException;

    void delete(ID id) throws EntityNotFoundException;

    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    Long count();

}

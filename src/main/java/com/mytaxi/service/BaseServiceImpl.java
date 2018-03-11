package com.mytaxi.service;

import com.mytaxi.domainobject.BaseDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import lombok.extern.apachecommons.CommonsLog;
import org.hibernate.Session;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


/**
 * Base Implementation of Common CRUD operations
 * <p/>
 */
@CommonsLog
@Service
public abstract class BaseServiceImpl<T extends BaseDO, ID extends Serializable> {


    protected abstract JpaRepository<T, ID> getRepository();

    @PersistenceContext
    protected EntityManager entityManager;

    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }


    public T find(ID id) throws EntityNotFoundException {
        T entity = getRepository().findOne(id);
        if (Objects.isNull(entity)) {
            throw new EntityNotFoundException("Could not find entity with id: " + id);
        }
        return entity;
    }

    public T create(T entity) throws ConstraintsViolationException {
        try {
            entity = getRepository().save(entity);
        } catch (DataIntegrityViolationException e) {
            log.warn("Some constraints are thrown due to Entity creation: " + entity.getClass(), e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return entity;
    }

    @Transactional
    public void delete(ID id) throws EntityNotFoundException {
        T entity = find(id);
        entity.setDeleted(true);
    }

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    public Long count() {
        return getRepository().count();
    }

}
package edu.bbte.idde.lkim2156.spring.dao;


import edu.bbte.idde.lkim2156.spring.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface Dao<T extends BaseEntity> {
    Page<T> findAll(Pageable pageable) throws NotFoundException;


    // T findById(Integer id) throws NotFoundException;
    Optional<T> findById(Integer id) throws NotFoundException;

    //T updateById(T entity) throws NotFoundException;
    T saveAndFlush(T entity) throws NotFoundException;

    // T create(T entity) throws NotFoundException;
    T save(T entity) throws NotFoundException;

    void deleteById(Integer id) throws NotFoundException;
}
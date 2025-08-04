package edu.bbte.idde.lkim2156.backend.dao;


import java.util.List;

public interface Dao<T> {
    List<T> findAll() throws NotFoundException;

    T findById(Integer id) throws NotFoundException;

    T updateById(Integer id, T entity) throws NotFoundException;

    T create(T entity) throws NotFoundException;

    void deleteById(Integer id) throws NotFoundException;
}
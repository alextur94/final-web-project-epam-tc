package com.epam.jwd.dao.api;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.Entity;

import java.util.List;

public interface Dao<T extends Entity<K>, K> {
    T save(T entity) throws DaoException;
    Boolean update(T entity) throws DaoException;
    Boolean delete(T entity) throws DaoException;
    T findById(K id) throws DaoException;
    List<T> findAll() throws DaoException;
}

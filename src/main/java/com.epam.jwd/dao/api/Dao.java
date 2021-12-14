package com.epam.jwd.dao.api;

import com.epam.jwd.dao.model.Entity;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T extends Entity<K>, K> {
    T save(T entity) throws SQLException;
    Boolean update(T entity);
    Boolean delete(T entity);
    T findById(K id);
    List<T> findAll();
}
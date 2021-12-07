package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.AbstractDto;
import com.epam.jwd.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface Service<T extends AbstractDto<K>, K> {
    T save(T value) throws ServiceException, SQLException;
    Boolean update(T value) throws ServiceException;
    Boolean delete(T value) throws ServiceException;
    T getById(K id) throws ServiceException;
    List<T> getAll() throws ServiceException;
}

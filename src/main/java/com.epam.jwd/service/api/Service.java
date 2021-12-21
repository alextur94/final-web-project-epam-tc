package com.epam.jwd.service.api;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.service.dto.AbstractDto;
import com.epam.jwd.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface Service<T extends AbstractDto<K>, K> {
    T create(T value) throws ServiceException, SQLException, DaoException;
    Boolean update(T value) throws ServiceException, DaoException;
    Boolean delete(T value) throws ServiceException, DaoException;
    T getById(K id) throws ServiceException, DaoException;
    List<T> getAll() throws ServiceException, DaoException;
}

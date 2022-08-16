package com.epam.jwd.dao.api;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.price.Price;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CarDao extends Dao<Car, Integer> {
    List<Car> findByRange(Integer skip, Integer size) throws DaoException;
    Boolean saveCarPrice(Car car, Price price) throws DaoException;
    Boolean updateCarById(Car car, Connection connection) throws SQLException;
    Integer countRowFromCars() throws DaoException;
}

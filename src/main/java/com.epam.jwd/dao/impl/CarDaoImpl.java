package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.price.Price;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarDaoImpl implements Dao<Car, Integer> {
    private static final Logger logger = LogManager.getLogger(CarDaoImpl.class);
    private PriceDaoImpl priceDao = new PriceDaoImpl();

    private static final String SQL_SAVE_CAR = "INSERT INTO car (brand, model, year, level, body, engine_volume, transmission, doors, color, available, price_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_CAR_BY_ID = "UPDATE car SET brand = ?, model = ?, year = ?, level = ?, body = ?, engine_volume = ?, transmission = ?, doors = ?, color = ?, available = ?, price_id = ? WHERE id = ?";
    private static final String SQL_DELETE_CAR_BY_ID = "DELETE FROM car WHERE id = ?";
    private static final String SQL_FIND_ALL_CARS = "SELECT id, brand, model, year, level, body, engine_volume, transmission, doors, color, available, price_id FROM car";
    private static final String SQL_FIND_CAR_BY_ID = "SELECT id, brand, model, year, level, body, engine_volume, transmission, doors, color, available, price_id FROM car WHERE id = ?";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Car save(Car entity) {
        logger.info("save method " + CarDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            Car car;
            connection.setAutoCommit(false);
            car = saveCar(entity, connection);
            connection.commit();
            connection.setAutoCommit(true);
            return car;
        } catch (SQLException throwables) {
            logger.error(throwables);
            return null;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(Car car) {
        logger.info("update method " + CarDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return updateCarById(car, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Car car) {
        logger.info("delete method " + CarDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return deleteCarById(car.getId(), connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Car> findAll() {
        logger.info("find all method " + CarDaoImpl.class);
        List<Car> car = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        try {
            car = findAllCars(connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return car;
    }

    @Override
    public Car findById(Integer id) {
        logger.info("find by id method " + CarDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        Car car = null;
        try {
            car = findCarById(id, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return car;
    }

    private Car saveCar(Car car, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_SAVE_CAR, new String[] {"id"});
        statement.setString(1, car.getBrand());
        statement.setString(2, car.getModel());
        statement.setInt(3, car.getYear());
        statement.setByte(4, car.getLevel());
        statement.setByte(5, car.getBody());
        statement.setInt(6, car.getEngineVolume());
        statement.setBoolean(7, car.getTransmission());
        statement.setByte(8, car.getDoors());
        statement.setString(9, car.getColor());
        statement.setBoolean(10, car.getAvailable());
        statement.setInt(11, car.getPriceId());
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        car.setId(id);
        statement.close();
        return car;
    }

    private Boolean updateCarById(Car car, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CAR_BY_ID);
        statement.setString(1, car.getBrand());
        statement.setString(2, car.getModel());
        statement.setInt(3, car.getYear());
        statement.setByte(4, car.getLevel());
        statement.setByte(5, car.getBody());
        statement.setInt(6, car.getEngineVolume());
        statement.setBoolean(7, car.getTransmission());
        statement.setByte(8, car.getDoors());
        statement.setString(9, car.getColor());
        statement.setBoolean(10, car.getAvailable());
        statement.setInt(11, car.getPriceId());
        statement.setInt(12, car.getId());
        Boolean result = Objects.equals(statement.executeUpdate(), 1);
        statement.close();
        return result;
    }

    private Boolean deleteCarById(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CAR_BY_ID);
        statement.setInt(1, id);
        Boolean result = Objects.equals(statement.executeUpdate(), 1);
        statement.close();
        return result;
    }

    private List<Car> findAllCars(Connection connection) throws SQLException {
        List<Car> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_CARS);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Car car = new Car.Builder()
                    .withId(resultSet.getInt(1))
                    .withBrand(resultSet.getString(2))
                    .withModel(resultSet.getString(3))
                    .withYear(resultSet.getInt(4))
                    .withLevel(resultSet.getByte(5))
                    .withBody(resultSet.getByte(6))
                    .withEngineVolume(resultSet.getInt(7))
                    .withTransmission(resultSet.getBoolean(8))
                    .withDoors(resultSet.getByte(9))
                    .withColor(resultSet.getString(10))
                    .withAvailable(resultSet.getBoolean(11))
                    .withPriceId(resultSet.getInt(12))
                    .build();
            result.add(car);
        }
        statement.close();
        resultSet.close();
        return result;
    }

    private Car findCarById(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_CAR_BY_ID);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Car car;
        if (resultSet.next()) {
            car = new Car.Builder()
                    .withId(resultSet.getInt(1))
                    .withBrand(resultSet.getString(2))
                    .withModel(resultSet.getString(3))
                    .withYear(resultSet.getInt(4))
                    .withLevel(resultSet.getByte(5))
                    .withBody(resultSet.getByte(6))
                    .withEngineVolume(resultSet.getInt(7))
                    .withTransmission(resultSet.getBoolean(8))
                    .withDoors(resultSet.getByte(9))
                    .withColor(resultSet.getString(10))
                    .withAvailable(resultSet.getBoolean(11))
                    .withPriceId(resultSet.getInt(12))
                    .build();
        } else {
            car = null;
        }
        statement.close();
        resultSet.close();
        return car;
    }

    public Boolean saveCarPrice(Car car, Price price) {
        logger.info("save car and price method " + CarDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            priceDao.savePrice(price, connection);
            car.setPriceId(price.getId());
            saveCar(car, connection);
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException throwables) {
            logger.error(throwables);
            throw new DaoException(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}

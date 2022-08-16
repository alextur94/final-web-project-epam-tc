package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.CarDao;
import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.api.Message;
import com.epam.jwd.dao.api.PriceDao;
import com.epam.jwd.dao.connectionpool.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.price.Price;
import com.epam.jwd.dao.sql.SqlQueries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarDaoImpl implements CarDao {
    private static final Logger logger = LogManager.getLogger(CarDaoImpl.class);
    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    private PriceDao priceDao = new PriceDaoImpl();

    @Override
    public Car save(Car car) throws DaoException {
        logger.info("save method " + CarDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return saveCar(car, connection);
        } catch (SQLException e) {
            throw new DaoException(Message.SAVE_CAR_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(Car car) throws DaoException {
        logger.info("update method " + CarDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return updateCarById(car, connection);
        } catch (SQLException e) {
            throw new DaoException(Message.UPDATE_CAR_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Car car) throws DaoException {
        logger.info("delete method " + CarDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return deleteCarById(car.getId(), connection);
        } catch (SQLException e) {
            throw new DaoException(Message.DELETE_CAR_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Car> findAll() throws DaoException {
        logger.info("find all method " + CarDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return findAllCars(connection);
        } catch (SQLException e) {
            throw new DaoException(Message.FIND_ALL_CARS_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Car findById(Integer id) throws DaoException {
        logger.info("find by id method " + CarDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        Car car;
        try {
            car = findCarById(id, connection);
            if (car != null) {
                return car;
            }
            throw new DaoException(Message.FIND_BY_ID_CAR_ERROR);
        } catch (SQLException throwables) {
            throw new DaoException(Message.FIND_BY_ID_CAR_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Car> findByRange(Integer skip, Integer size) throws DaoException {
        logger.info("find by range method " + CarDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            List<Car> result = new ArrayList<>();
            ResultSet resultSet;
            try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_BY_RANGE)) {
                statement.setInt(1, skip);
                statement.setInt(2, size);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Car car = new Car.Builder()
                            .withId(resultSet.getInt(1))
                            .withBrand(resultSet.getString(2))
                            .withModel(resultSet.getString(3))
                            .withAvailable(resultSet.getByte(4))
                            .withPriceId(resultSet.getInt(5))
                            .build();
                    result.add(car);
                }
            }
            resultSet.close();
            return result;
        } catch (SQLException e) {
            throw new DaoException(Message.FIND_BY_RANGE_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean saveCarPrice(Car car, Price price) throws DaoException {
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
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
            logger.error(Message.SAVE_CAR_AND_PRICE_ERROR, throwables);
            throw new DaoException(Message.SAVE_CAR_AND_PRICE_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private Car saveCar(Car car, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_SAVE_CAR, new String[]{"id"})) {
            statement.setString(1, car.getBrand());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setByte(4, car.getLevel());
            statement.setByte(5, car.getBody());
            statement.setInt(6, car.getEngineVolume());
            statement.setByte(7, car.getTransmission());
            statement.setByte(8, car.getDoors());
            statement.setString(9, car.getColor());
            statement.setByte(10, car.getAvailable());
            statement.setInt(11, car.getPriceId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Integer id = resultSet.getInt(1);
            car.setId(id);
        }
        return car;
    }

    @Override
    public Boolean updateCarById(Car car, Connection connection) throws SQLException {
        Boolean result;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_UPDATE_CAR_BY_ID)) {
            statement.setString(1, car.getBrand());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setByte(4, car.getLevel());
            statement.setByte(5, car.getBody());
            statement.setInt(6, car.getEngineVolume());
            statement.setByte(7, car.getTransmission());
            statement.setByte(8, car.getDoors());
            statement.setString(9, car.getColor());
            statement.setByte(10, car.getAvailable());
            statement.setInt(11, car.getPriceId());
            statement.setInt(12, car.getId());
            result = Objects.equals(statement.executeUpdate(), 1);
        }
        return result;
    }

    private Boolean deleteCarById(Integer id, Connection connection) throws SQLException {
        Boolean result;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_DELETE_CAR_BY_ID)) {
            statement.setInt(1, id);
            result = Objects.equals(statement.executeUpdate(), 1);
        }
        return result;
    }

    private List<Car> findAllCars(Connection connection) throws SQLException {
        List<Car> result = new ArrayList<>();
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_ALL_CARS)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Car car = new Car.Builder()
                        .withId(resultSet.getInt(1))
                        .withBrand(resultSet.getString(2))
                        .withModel(resultSet.getString(3))
                        .withYear(resultSet.getInt(4))
                        .withLevel(resultSet.getByte(5))
                        .withBody(resultSet.getByte(6))
                        .withEngineVolume(resultSet.getInt(7))
                        .withTransmission(resultSet.getByte(8))
                        .withDoors(resultSet.getByte(9))
                        .withColor(resultSet.getString(10))
                        .withAvailable(resultSet.getByte(11))
                        .withPriceId(resultSet.getInt(12))
                        .build();
                result.add(car);
            }
        }
        resultSet.close();
        return result;
    }

    private Car findCarById(Integer id, Connection connection) throws SQLException {
        ResultSet resultSet;
        Car car;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_CAR_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                car = new Car.Builder()
                        .withId(resultSet.getInt(1))
                        .withBrand(resultSet.getString(2))
                        .withModel(resultSet.getString(3))
                        .withYear(resultSet.getInt(4))
                        .withLevel(resultSet.getByte(5))
                        .withBody(resultSet.getByte(6))
                        .withEngineVolume(resultSet.getInt(7))
                        .withTransmission(resultSet.getByte(8))
                        .withDoors(resultSet.getByte(9))
                        .withColor(resultSet.getString(10))
                        .withAvailable(resultSet.getByte(11))
                        .withPriceId(resultSet.getInt(12))
                        .build();
            } else {
                car = null;
            }
        }
        resultSet.close();
        return car;
    }

    @Override
    public Integer countRowFromCars() throws DaoException {
        logger.info("save car and price method " + CarDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        Integer numRow = 0;
        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) FROM car")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                numRow = resultSet.getInt(1);
            }
            resultSet.close();
            return numRow;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DaoException("error");
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}

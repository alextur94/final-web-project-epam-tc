package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.api.Message;
import com.epam.jwd.dao.connectionpool.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.exception.DaoException;
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

public class PriceDaoImpl implements Dao<Price, Integer> {
    private static final Logger logger = LogManager.getLogger(PriceDaoImpl.class);
    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Price save(Price price) throws DaoException {
        logger.info("save method " + PriceDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return savePrice(price, connection);
        } catch (SQLException throwables) {
            logger.error(Message.SAVE_PRICE_ERROR, throwables);
            throw new DaoException(Message.SAVE_PRICE_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(Price price) throws DaoException {
        logger.info("update method " + PriceDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return updatePriceById(price, connection);
        } catch (SQLException throwables) {
            logger.error(Message.UPDATE_PRICE_ERROR, throwables);
            throw new DaoException(Message.UPDATE_PRICE_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Price price) throws DaoException {
        logger.info("delete method " + PriceDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return deletePriceById(price.getId(), connection);
        } catch (SQLException throwables) {
            logger.error(Message.DELETE_PRICE_ERROR, throwables);
            throw new DaoException(Message.DELETE_PRICE_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Price> findAll() throws DaoException {
        logger.info("find all method " + PriceDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return findAllPrices(connection);
        } catch (SQLException throwables) {
            logger.error(Message.FIND_ALL_PRICE_ERROR, throwables);
            throw new DaoException(Message.FIND_ALL_PRICE_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Price findById(Integer id) throws DaoException {
        logger.info("find by id method " + PriceDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        Price price;
        try {
            price = findPriceById(id, connection);
            if (price != null) {
                return price;
            }
            logger.error(Message.FIND_BY_ID_PRICE_ERROR);
            throw new DaoException(Message.FIND_BY_ID_PRICE_ERROR);
        } catch (SQLException throwables) {
            logger.error(Message.FIND_BY_ID_PRICE_ERROR, throwables);
            throw new DaoException(Message.FIND_BY_ID_PRICE_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public Price savePrice(Price price, Connection connection) throws SQLException {
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_SAVE_PRICE, new String[]{"id"})) {
            statement.setDouble(1, price.getPricePerDay());
            statement.setDouble(2, price.getPricePerHour());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Integer id = resultSet.getInt(1);
            price.setId(id);
        }
        resultSet.close();
        return price;
    }

    private Boolean updatePriceById(Price price, Connection connection) throws SQLException {
        Boolean result;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_UPDATE_PRICE_BY_ID)) {
            statement.setDouble(1, price.getPricePerDay());
            statement.setDouble(2, price.getPricePerHour());
            statement.setInt(3, price.getId());
            result = Objects.equals(statement.executeUpdate(), 1);
        }
        return result;
    }

    private Boolean deletePriceById(Integer id, Connection connection) throws SQLException {
        Boolean result;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_DELETE_PRICE_BY_ID)) {
            statement.setInt(1, id);
            result = Objects.equals(statement.executeUpdate(), 1);
        }
        return result;
    }

    private List<Price> findAllPrices(Connection connection) throws SQLException {
        List<Price> result = new ArrayList<>();
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_ALL_PRICES)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Price price = new Price(
                        resultSet.getInt(1),
                        resultSet.getDouble(2),
                        resultSet.getDouble(3)
                );
                result.add(price);
            }
        }
        resultSet.close();
        return result;
    }

    private Price findPriceById(Integer id, Connection connection) throws SQLException {
        ResultSet resultSet;
        Price price;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_PRICE_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                price = new Price(
                        resultSet.getInt(1),
                        resultSet.getDouble(2),
                        resultSet.getDouble(3)
                );

            } else {
                price = null;
            }
        }
        resultSet.close();
        return price;
    }
}

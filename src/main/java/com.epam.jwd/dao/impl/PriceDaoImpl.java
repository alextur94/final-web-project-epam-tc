package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;

//import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;

import com.epam.jwd.dao.model.price.Price;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PriceDaoImpl implements Dao<Price, Integer> {
    private static final Logger logger = LogManager.getLogger(PriceDaoImpl.class);

    private static final String SQL_SAVE_PRICE = "INSERT INTO price (price_per_day, price_per_hour) VALUES (?,?)";
    private static final String SQL_UPDATE_PRICE_BY_ID = "UPDATE price SET price_per_day = ?, price_per_hour = ? WHERE id = ?";
    private static final String SQL_DELETE_PRICE_BY_ID = "DELETE FROM price WHERE id = ?";
    private static final String SQL_FIND_ALL_PRICES = "SELECT id, price_per_day, price_per_hour FROM price";
    private static final String SQL_FIND_PRICE_BY_ID = "SELECT id, price_per_day, price_per_hour FROM price WHERE id = ?";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Price save(Price entity) {
        logger.info("save method " + PriceDaoImpl.class);
        Connection connection = connectionPool.requestConnection();
        try {
            Price price;
            connection.setAutoCommit(false);
            price = savePrice(entity, connection);
            connection.commit();
            connection.setAutoCommit(true);
            return price;
        } catch (SQLException throwables) {
            logger.error(throwables);
            return null;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(Price price) {
        logger.info("update method " + PriceDaoImpl.class);
//        Connection connection = connectionPool.takeConnection();
        Connection connection = connectionPool.requestConnection();
        try {
            return updatePriceById(price, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Price price) {
        logger.info("delete method " + PriceDaoImpl.class);
//        Connection connection = connectionPool.takeConnection();
        Connection connection = connectionPool.requestConnection();
        try {
            return deletePriceById(price.getId(), connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Price> findAll() {
        logger.info("find all method " + PriceDaoImpl.class);
        List<Price> price = new ArrayList<>();
        Connection connection = connectionPool.requestConnection();
        try {
            price = findAllPrices(connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return price;
    }

    @Override
    public Price findById(Integer id) {
        logger.info("find by id method " + PriceDaoImpl.class);
        Connection connection = connectionPool.requestConnection();
        Price price = null;
        try {
            price = findPriceById(id, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return price;
    }

    public Price savePrice(Price price, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_SAVE_PRICE, new String[] {"id"});
        statement.setDouble(1, price.getPricePerDay());
        statement.setDouble(2, price.getPricePerHour());
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        price.setId(id);
        statement.close();
        resultSet.close();
        return price;
    }

    private Boolean updatePriceById(Price price, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PRICE_BY_ID);
        statement.setDouble(1, price.getPricePerDay());
        statement.setDouble(2, price.getPricePerHour());
        statement.setInt(3, price.getId());
        Boolean result = Objects.equals(statement.executeUpdate(), 1);
        statement.close();
        return result;
    }

    private Boolean deletePriceById(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PRICE_BY_ID);
        statement.setInt(1, id);
        Boolean result = Objects.equals(statement.executeUpdate(), 1);
        statement.close();
        return result;
    }

    private List<Price> findAllPrices(Connection connection) throws SQLException {
        List<Price> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_PRICES);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Price price = new Price(
                    resultSet.getInt(1),
                    resultSet.getDouble(2),
                    resultSet.getDouble(3)
            );
            result.add(price);
        }
        statement.close();
        resultSet.close();
        return result;
    }

    private Price findPriceById(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_PRICE_BY_ID);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Price price;
        if (resultSet.next()) {
            price = new Price(
                    resultSet.getInt(1),
                    resultSet.getDouble(2),
                    resultSet.getDouble(3)
                    );

        } else {
            price = null;
        }
        statement.close();
        resultSet.close();
        return price;
    }
}

package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.*;
import com.epam.jwd.dao.connectionpool.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.insurance.Insurance;
import com.epam.jwd.dao.model.mark.Mark;
import com.epam.jwd.dao.model.order.Order;
import com.epam.jwd.dao.sql.SqlQueries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = LogManager.getLogger(CarDaoImpl.class);
    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    private AccountDao accountDao = new AccountDaoImpl();
    private CarDao carDao = new CarDaoImpl();
    private MarkDao markDao = new MarkDaoImpl();

    @Override
    public Order save(Order order) throws DaoException {
        logger.info("save method " + OrderDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return saveOrder(connection, order);
        } catch (SQLException e) {
            throw new DaoException(Message.SAVE_ORDER_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(Order order) throws DaoException {
        logger.info("update method " + OrderDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return updateOrderById(order, connection);
        } catch (SQLException e) {
            throw new DaoException(Message.UPDATE_ORDER_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Order order) throws DaoException {
        logger.info("delete method " + OrderDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return deleteOrderById(order.getId(), connection);
        } catch (SQLException e) {
            throw new DaoException(Message.DELETE_ORDER_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Order> findAll() throws DaoException {
        logger.info("find all method " + OrderDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return findAllOrders(connection);
        } catch (SQLException e) {
            throw new DaoException(Message.FIND_ALL_ORDERS_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Order findById(Integer id) throws DaoException {
        logger.info("find by id method " + OrderDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        Order order;
        try {
            order = findOrderById(id, connection);
            if (order != null) {
                return order;
            }
            throw new DaoException(Message.FIND_BY_ID_ORDER_ERROR);
        } catch (SQLException e) {
            throw new DaoException(Message.FIND_BY_ID_ORDER_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Order> findByStatus(Integer status) throws DaoException {
        logger.info("find by status new method " + OrderDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return findAllOrdersByStatus(status, connection);
        } catch (SQLException e) {
            throw new DaoException(Message.FIND_USER_BY_ID_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Order> findCountOrdersInBase(Integer offset, Integer count, Integer userId) throws DaoException {
        logger.info("find count orders method " + OrderDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return findAllOrdersByUserId(offset, count, userId, connection);
        } catch (SQLException e) {
            throw new DaoException(Message.FIND_COUNT_ORDERS_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Integer countOrdersByUser(Integer userId) throws DaoException {
        logger.info("find count orders method " + OrderDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_COUNT_ORDERS)) {
            Integer numRow = 0;
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                numRow = resultSet.getInt(1);
            }
            resultSet.close();
            return numRow;
        } catch (SQLException e) {
            throw new DaoException(Message.FIND_USER_BY_ID_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean saveOrderMarkInsurance(Mark mark, Insurance insurance, Order order, Car car) throws DaoException {
        logger.info("save order mark insurance method " + OrderDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            Mark saveMark = new MarkDaoImpl().saveMark(mark, connection);
            Insurance saveInsurance = new InsuranceDaoImpl().saveInsurance(insurance, connection);
            order.setMarkId(saveMark.getId());
            order.setInsuranceId(saveInsurance.getId());
            saveOrder(connection, order);
            car.setAvailable((byte) 0);
            new CarDaoImpl().updateCarById(car, connection);
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
            throw new DaoException(Message.SAVE_ORDER_MARK_INSURANCE_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean cancelOrderAdmin(Account admin, Account person, Order order, Car car) throws DaoException {
        logger.info("cancel order admin method " + OrderDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            updateOrderById(order, connection);
            accountDao.updateAccountById(admin, connection);
            accountDao.updateAccountById(person, connection);
            carDao.updateCarById(car, connection);
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
            throw new DaoException(Message.CANCEL_ORDER_ADMIN_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean finishSaveOrder(Order order, Account account, Car car, Mark mark, Account admin) throws DaoException {
        logger.info("finish save order method " + OrderDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            updateOrderById(order, connection);
            accountDao.updateAccountById(account, connection);
            accountDao.updateAccountById(admin, connection);
            carDao.updateCarById(car, connection);
            markDao.updateMarkById(mark, connection);
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            throw new DaoException(Message.CANCEL_SAVE_FINISH_ORDER_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean OnlyOne(Integer userId) throws DaoException {
        logger.info("only one method " + OrderDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            Integer numRow = 0;
            try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_ONLY_ONE)) {
                statement.setInt(1, userId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    numRow = resultSet.getInt(1);
                }
                resultSet.close();
            }
            if (numRow == 0) {
                return true;
            }
            throw new DaoException(Message.ONLY_ONE_ACTIVE_ORDER_ERROR);
        } catch (SQLException e) {
            throw new DaoException(Message.ONLY_ONE_ACTIVE_ORDER_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private Order saveOrder(Connection connection, Order order) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_SAVE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setByte(1, order.getStatus());
            statement.setInt(2, order.getRentalTime());
            statement.setByte(3, order.getPaymentStatus());
            statement.setDouble(4, order.getPledge());
            statement.setDouble(5, order.getCurrentSum());
            statement.setInt(6, order.getUserId());
            statement.setInt(7, order.getCarId());
            statement.setInt(8, order.getMarkId());
            statement.setInt(9, order.getInsuranceId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Integer id = resultSet.getInt(1);
            order.setId(id);
        }
        return order;
    }

    @Override
    public Boolean updateOrderById(Order order, Connection connection) throws SQLException {
        Boolean result;
        PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_UPDATE_ORDER_BY_ID);
        statement.setByte(1, order.getStatus());
        statement.setInt(2, order.getRentalTime());
        statement.setByte(3, order.getPaymentStatus());
        statement.setLong(4, order.getRentStartDtm());
        statement.setLong(5, order.getRentEndDtm());
        statement.setInt(6, order.getStartLevel());
        statement.setInt(7, order.getEndLevel());
        statement.setString(8, order.getRefusal());
        statement.setDouble(9, order.getPledge());
        statement.setDouble(10, order.getCurrentSum());
        statement.setByte(11, order.getStatusMark());
        statement.setInt(12, order.getUserId());
        statement.setInt(13, order.getCarId());
        statement.setInt(14, order.getMarkId());
        statement.setInt(15, order.getInsuranceId());
        statement.setInt(16, order.getId());
        result = Objects.equals(statement.executeUpdate(), 1);
        statement.close();
        return result;
    }

    private Boolean deleteOrderById(Integer id, Connection connection) throws SQLException {
        Boolean result;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_DELETE_ORDER_BY_ID)) {
            statement.setInt(1, id);
            result = Objects.equals(statement.executeUpdate(), 1);
        }
        return result;
    }

    private List<Order> findAllOrders(Connection connection) throws SQLException {
        List<Order> result = new ArrayList<>();
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_ALL_ORDERS)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order.Builder()
                        .withId(resultSet.getInt(1))
                        .withStatus(resultSet.getByte(2))
                        .withRentalTime(resultSet.getInt(3))
                        .withPaymentStatus(resultSet.getByte(4))
                        .withRentStartDtm(resultSet.getLong(5))
                        .withRentEndDtm(resultSet.getLong(6))
                        .withStartLevel(resultSet.getInt(7))
                        .withEndLevel(resultSet.getInt(8))
                        .withRefusal(resultSet.getString(9))
                        .withPledge(resultSet.getDouble(10))
                        .withCurrentSum(resultSet.getDouble(11))
                        .withStatusMark(resultSet.getByte(12))
                        .withUserId(resultSet.getInt(13))
                        .withCarId(resultSet.getInt(14))
                        .withMarkId(resultSet.getInt(15))
                        .withInsuranceId(resultSet.getInt(16))
                        .build();
                result.add(order);
            }
        }
        resultSet.close();
        return result;
    }

    private List<Order> findAllOrdersByUserId(Integer offset, Integer size, Integer userId, Connection connection) throws SQLException {
        List<Order> result = new ArrayList<>();
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_ORDER_BY_USER_ID)) {
            statement.setInt(1, userId);
            statement.setInt(2, size);
            statement.setInt(3, offset);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order.Builder()
                        .withId(resultSet.getInt(1))
                        .withStatus(resultSet.getByte(2))
                        .withRentalTime(resultSet.getInt(3))
                        .withPaymentStatus(resultSet.getByte(4))
                        .withRentStartDtm(resultSet.getLong(5))
                        .withRentEndDtm(resultSet.getLong(6))
                        .withStartLevel(resultSet.getInt(7))
                        .withEndLevel(resultSet.getInt(8))
                        .withRefusal(resultSet.getString(9))
                        .withPledge(resultSet.getDouble(10))
                        .withCurrentSum(resultSet.getDouble(11))
                        .withStatusMark(resultSet.getByte(12))
                        .withUserId(resultSet.getInt(13))
                        .withCarId(resultSet.getInt(14))
                        .withMarkId(resultSet.getInt(15))
                        .withInsuranceId(resultSet.getInt(16))
                        .build();
                result.add(order);
            }
        }
        resultSet.close();
        return result;
    }

    private Order findOrderById(Integer id, Connection connection) throws SQLException {
        ResultSet resultSet;
        Order order;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_ORDER_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = new Order.Builder()
                        .withId(resultSet.getInt(1))
                        .withStatus(resultSet.getByte(2))
                        .withRentalTime(resultSet.getInt(3))
                        .withPaymentStatus(resultSet.getByte(4))
                        .withRentStartDtm(resultSet.getLong(5))
                        .withRentEndDtm(resultSet.getLong(6))
                        .withStartLevel(resultSet.getInt(7))
                        .withEndLevel(resultSet.getInt(8))
                        .withRefusal(resultSet.getString(9))
                        .withPledge(resultSet.getDouble(10))
                        .withCurrentSum(resultSet.getDouble(11))
                        .withStatusMark(resultSet.getByte(12))
                        .withUserId(resultSet.getInt(13))
                        .withCarId(resultSet.getInt(14))
                        .withMarkId(resultSet.getInt(15))
                        .withInsuranceId(resultSet.getInt(16))
                        .build();
            } else {
                order = null;
            }
        }
        resultSet.close();
        return order;
    }

    private List<Order> findAllOrdersByStatus(Integer status, Connection connection) throws SQLException {
        List<Order> result = new ArrayList<>();
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_ORDER_BY_STATUS)) {
            statement.setInt(1, status);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order.Builder()
                        .withId(resultSet.getInt(1))
                        .withStatus(resultSet.getByte(2))
                        .withRentalTime(resultSet.getInt(3))
                        .withPaymentStatus(resultSet.getByte(4))
                        .withRentStartDtm(resultSet.getLong(5))
                        .withRentEndDtm(resultSet.getLong(6))
                        .withStartLevel(resultSet.getInt(7))
                        .withEndLevel(resultSet.getInt(8))
                        .withRefusal(resultSet.getString(9))
                        .withPledge(resultSet.getDouble(10))
                        .withCurrentSum(resultSet.getDouble(11))
                        .withStatusMark(resultSet.getByte(12))
                        .withUserId(resultSet.getInt(13))
                        .withCarId(resultSet.getInt(14))
                        .withMarkId(resultSet.getInt(15))
                        .withInsuranceId(resultSet.getInt(16))
                        .build();
                result.add(order);
            }
        }
        resultSet.close();
        return result;
    }
}

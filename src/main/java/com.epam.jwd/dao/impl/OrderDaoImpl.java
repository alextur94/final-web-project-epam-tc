package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;

//import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;

import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.insurance.Insurance;
import com.epam.jwd.dao.model.mark.Mark;
import com.epam.jwd.dao.model.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDaoImpl implements Dao<Order, Integer> {
    private static final Logger logger = LogManager.getLogger(CarDaoImpl.class);

//    private static final String SQL_SAVE_ORDER = "INSERT INTO order_tab (status, rental_time, payment_status, rent_start_dtm, rent_end_dtm, start_level, end_level, refusal, pledge, current_sum, real_sum, status_mark, user_id, car_id, mark_id, insurance_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_SAVE_ORDER = "INSERT INTO order_tab (status, rental_time, payment_status, pledge, current_sum, user_id, car_id, mark_id, insurance_id) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_ORDER_BY_ID = "UPDATE order_tab SET status = ?, rental_time = ?, payment_status = ?, rent_start_dtm = ?, rent_end_dtm = ?, start_level = ?, end_level = ?, refusal = ?, pledge = ?, current_sum = ?, real_sum = ?, status_mark = ?, user_id = ?, car_id = ?, mark_id = ?, insurance_id = ? WHERE id = ?";
    private static final String SQL_DELETE_ORDER_BY_ID = "DELETE FROM order_tab WHERE id = ?";
    private static final String SQL_FIND_ALL_ORDERS = "SELECT id, status, rental_time, payment_status, rent_start_dtm, rent_end_dtm, start_level, end_level, refusal, pledge, current_sum, real_sum, status_mark, user_id, car_id, mark_id, insurance_id FROM order_tab";
    private static final String SQL_FIND_ORDER_BY_ID = "SELECT id, status, rental_time, payment_status, rent_start_dtm, rent_end_dtm, start_level, end_level, refusal, pledge, current_sum, real_sum, status_mark, user_id, car_id, mark_id, insurance_id FROM order_tab WHERE id = ?";
    private static final String SQL_FIND_ORDER_BY_USER_ID = "SELECT id, status, rental_time, payment_status, rent_start_dtm, rent_end_dtm, start_level, end_level, refusal, pledge, current_sum, real_sum, status_mark, user_id, car_id, mark_id, insurance_id FROM order_tab WHERE user_id = ? ORDER BY id DESC";
    private static final String SQL_ONLY_ONE = "SELECT COUNT(*) FROM order_tab WHERE status != 4 AND status !=5 AND user_id = ?";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    private final Dao<Car, Integer> daoCar = new CarDaoImpl();
    private final Dao<Mark, Integer> daoMark = new MarkDaoImpl();
    private final Dao<Insurance, Integer> daoInsurance = new InsuranceDaoImpl();

    @Override
    public Order save(Order entity) {
        logger.info("save method " + OrderDaoImpl.class);
        Connection connection = connectionPool.requestConnection();
        try {
            Order order;
            order = saveOrder(connection, entity);
            return order;
        } catch (SQLException throwables) {
            logger.error(throwables);
            return null;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(Order order) {
        logger.info("update method " + OrderDaoImpl.class);
        Connection connection = connectionPool.requestConnection();
        try {
            return updateOrderById(order, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Order order) {
        logger.info("delete method " + OrderDaoImpl.class);
        Connection connection = connectionPool.requestConnection();
        try {
            return deleteOrderById(order.getId(), connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Order> findAll() {
        logger.info("find all method " + OrderDaoImpl.class);
        List<Order> orders = new ArrayList<>();
        Connection connection = connectionPool.requestConnection();
        try {
            orders = findAllOrders(connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orders;
    }

    @Override
    public Order findById(Integer id) {
        logger.info("find by id method " + OrderDaoImpl.class);
        Connection connection = connectionPool.requestConnection();
        Order order = null;
        try {
            order = findOrderById(id, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return order;
    }

    public List<Order> findByUserId(Integer userId) {
        logger.info("find by user id method " + OrderDaoImpl.class);
        List<Order> orders = new ArrayList<>();
        Connection connection = connectionPool.requestConnection();
        try {
            orders = findAllOrdersByUserId(userId, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orders;
    }

    public Boolean saveMarkOrderInsurance(Mark mark, Insurance insurance, Order order, Car car){
        logger.info("save order mark insurance method " + OrderDaoImpl.class);
        Connection connection = connectionPool.requestConnection();
        try {
            connection.setAutoCommit(false);

            Mark saveMark = ((MarkDaoImpl)daoMark).saveMark(mark, connection);
            Insurance saveInsurance = ((InsuranceDaoImpl)daoInsurance).saveInsurance(insurance, connection);
            order.setMarkId(saveMark.getId());
            order.setInsuranceId(saveInsurance.getId());
            saveOrder(connection, order);
            car.setAvailable((byte)0);
            ((CarDaoImpl)daoCar).updateCarById(car, connection);

            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException throwables) {
            logger.error(throwables);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private Order saveOrder(Connection connection, Order order) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_SAVE_ORDER, Statement.RETURN_GENERATED_KEYS);
        statement.setByte(1, order.getStatus());
        statement.setInt(2, order.getRentalTime());
        statement.setByte(3, order.getPaymentStatus());
//        statement.setLong(4, order.getRentStartDtm());
//        statement.setLong(5, order.getRentEndDtm());
//        statement.setInt(6, order.getStartLevel());
//        statement.setInt(7, order.getEndLevel());
//        statement.setString(8, order.getRefusal());
        statement.setDouble(4, order.getPledge());
        statement.setDouble(5, order.getCurrentSum());
//        statement.setDouble(11, order.getRealSum());
//        statement.setByte(12, order.getStatusMark());
        statement.setInt(6, order.getUserId());
        statement.setInt(7, order.getCarId());
        statement.setInt(8, order.getMarkId());
        statement.setInt(9, order.getInsuranceId());
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        order.setId(id);
        statement.close();
        return order;
    }

    private Boolean updateOrderById(Order order, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER_BY_ID);
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
        statement.setDouble(11, order.getRealSum());
        statement.setByte(12, order.getStatusMark());
        statement.setInt(13, order.getUserId());
        statement.setInt(14, order.getCarId());
        statement.setInt(15, order.getMarkId());
        statement.setInt(16, order.getInsuranceId());
        statement.setInt(17, order.getId());
        Boolean result = Objects.equals(statement.executeUpdate(), 1);
        statement.close();
        return result;
    }

    private Boolean deleteOrderById(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ORDER_BY_ID);
        statement.setInt(1, id);
        Boolean result = Objects.equals(statement.executeUpdate(), 1);
        statement.close();
        return result;
    }

    private List<Order> findAllOrders(Connection connection) throws SQLException {
        List<Order> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_ORDERS);
        ResultSet resultSet = statement.executeQuery();
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
                    .withRealSum(resultSet.getDouble(12))
                    .withStatusMark(resultSet.getByte(13))
                    .withUserId(resultSet.getInt(14))
                    .withCarId(resultSet.getInt(15))
                    .withMarkId(resultSet.getInt(16))
                    .withInsuranceId(resultSet.getInt(17))
                    .build();
            result.add(order);
        }
        statement.close();
        resultSet.close();
        return result;
    }

    private List<Order> findAllOrdersByUserId(Integer userId, Connection connection) throws SQLException {
        List<Order> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_ORDER_BY_USER_ID);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
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
                    .withRealSum(resultSet.getDouble(12))
                    .withStatusMark(resultSet.getByte(13))
                    .withUserId(resultSet.getInt(14))
                    .withCarId(resultSet.getInt(15))
                    .withMarkId(resultSet.getInt(16))
                    .withInsuranceId(resultSet.getInt(17))
                    .build();
            result.add(order);
        }
        statement.close();
        resultSet.close();
        return result;
    }

    private Order findOrderById(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_ORDER_BY_ID);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Order order;
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
                    .withRealSum(resultSet.getDouble(12))
                    .withStatusMark(resultSet.getByte(13))
                    .withUserId(resultSet.getInt(14))
                    .withCarId(resultSet.getInt(15))
                    .withMarkId(resultSet.getInt(16))
                    .withInsuranceId(resultSet.getInt(17))
                    .build();
        } else {
            order = null;
        }
        statement.close();
        resultSet.close();
        return order;
    }

    public Boolean OnlyOne(Integer userId) {
        logger.info("only one method " + OrderDaoImpl.class);
        Connection connection = connectionPool.requestConnection();
        Boolean result;
        try {
            Integer numRow = 0;
            PreparedStatement statement = connection.prepareStatement(SQL_ONLY_ONE);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                numRow = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
            if (numRow == 0) {
                result = true;
            } else {
                result = false;
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
            result = false;
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }
}

package com.epam.jwd.dao.api;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.insurance.Insurance;
import com.epam.jwd.dao.model.mark.Mark;
import com.epam.jwd.dao.model.order.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao extends Dao<Order, Integer> {
    List<Order> findByStatus(Integer status) throws DaoException;
    List<Order> findCountOrdersInBase(Integer offset, Integer count, Integer userId) throws DaoException;
    Integer countOrdersByUser(Integer userId) throws DaoException;
    Boolean saveOrderMarkInsurance(Mark mark, Insurance insurance, Order order, Car car) throws DaoException, SQLException;
    Boolean cancelOrderAdmin(Account admin, Account person, Order order, Car car) throws DaoException, SQLException;
    Boolean finishSaveOrder(Order order, Account account, Car car, Mark mark, Account admin) throws DaoException;
    Boolean OnlyOne(Integer userId) throws DaoException;
    Boolean updateOrderById(Order order, Connection connection) throws SQLException;
}

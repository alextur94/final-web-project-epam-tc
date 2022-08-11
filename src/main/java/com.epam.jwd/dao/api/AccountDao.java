package com.epam.jwd.dao.api;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.order.Order;

import java.sql.Connection;
import java.sql.SQLException;

public interface AccountDao extends Dao<Account, Integer> {
    Account findByEmail(String email) throws DaoException;
    Boolean saveTransactionUserAdmin(Account user, Account admin, Order order) throws DaoException, SQLException;
    Boolean cancelOrder(Account account, Account admin, Car car, Order order) throws DaoException, SQLException;
    Account saveAccount(Account account, Connection connection) throws SQLException;
    Boolean updateAccountById(Account account, Connection connection) throws SQLException;
}

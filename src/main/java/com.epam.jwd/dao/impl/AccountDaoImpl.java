package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.AccountDao;
import com.epam.jwd.dao.api.Message;
import com.epam.jwd.dao.connectionpool.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.account.Role;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.order.Order;
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

public class AccountDaoImpl implements AccountDao {

    private static final Logger logger = LogManager.getLogger(AccountDaoImpl.class);
    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Account save(Account account) {
        return null;
    }

    @Override
    public Boolean update(Account account) throws DaoException {
        logger.info("update method " + AccountDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return updateAccountById(account, connection);
        } catch (SQLException e) {
            throw new DaoException(Message.UPDATE_ACCOUNT_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Account account) {
        return null;
    }

    @Override
    public List<Account> findAll() throws DaoException {
        logger.info("find all method " + AccountDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return findAllAccounts(connection);
        } catch (SQLException e) {
            throw new DaoException(Message.FIND_ALL_ACCOUNTS_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Account findById(Integer id) throws DaoException {
        logger.info("find by id method " + AccountDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        Account account;
        try {
            account = findAccountById(id, connection);
            if (account != null) {
                return account;
            }
            throw new DaoException(Message.FIND_BY_ID_ACCOUNT_ERROR);
        } catch (SQLException e) {
            throw new DaoException(Message.FIND_BY_ID_ACCOUNT_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Account findByEmail(String email) throws DaoException {
        logger.info("find by email method " + AccountDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        Account account;
        try {
            account = findAccountByEmail(email, connection);
            if (account == null) {
                return account;
            }
            throw new DaoException(Message.FIND_BY_EMAIL_ERROR);
        } catch (SQLException e) {
            throw new DaoException(Message.FIND_BY_EMAIL_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean saveTransactionUserAdmin(Account user, Account admin, Order order) throws DaoException {
        logger.info("save transaction account to admin method " + UserDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            updateAccountById(user, connection);
            updateAccountById(admin, connection);
            new OrderDaoImpl().updateOrderById(order, connection);
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw new DaoException(Message.ROLLBACK_ERROR);
            }
            throw new DaoException(Message.TRANSACTION_ACCOUNT_ADMIN_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean cancelOrder(Account account, Account admin, Car car, Order order) throws DaoException {
        logger.info("cancel transaction method " + UserDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            updateAccountById(account, connection);
            updateAccountById(admin, connection);
            new CarDaoImpl().updateCarById(car, connection);
            new OrderDaoImpl().updateOrderById(order, connection);
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw new DaoException(Message.ROLLBACK_ERROR);
            }
            throw new DaoException(Message.CANCEL_ORDER_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Account saveAccount(Account account, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_SAVE_ACCOUNT, new String[]{"id"});
        statement.setInt(1, account.getRole().getRoleId());
        statement.setString(2, account.getName());
        statement.setString(3, account.getSurname());
        statement.setString(4, account.getEmail());
        statement.setString(5, account.getPhone());
        statement.setString(6, account.getDocumentId());
        statement.setString(7, account.getAddress());
        statement.setString(8, account.getDriveLicenseNumber());
        statement.setDouble(9, account.getBalance());
        statement.setInt(10, account.getStatus());
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        account.setId(id);
        statement.close();
        resultSet.close();
        return account;
    }

    @Override
    public Boolean updateAccountById(Account account, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_UPDATE_ACCOUNT_BY_ID);
        statement.setInt(1, account.getRole().getRoleId());
        statement.setString(2, account.getName());
        statement.setString(3, account.getSurname());
        statement.setString(4, account.getEmail());
        statement.setString(5, account.getPhone());
        statement.setString(6, account.getDocumentId());
        statement.setString(7, account.getAddress());
        statement.setString(8, account.getDriveLicenseNumber());
        statement.setDouble(9, account.getBalance());
        statement.setInt(10, account.getStatus());
        statement.setInt(11, account.getId());
        Boolean result = Objects.equals(statement.executeUpdate(), 1);
        statement.close();
        return result;
    }

    private List<Account> findAllAccounts(Connection connection) throws SQLException {
        List<Account> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_ALL_ACCOUNTS);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Account account = new Account.Builder()
                    .withId(resultSet.getInt(1))
                    .withRole(Role.getById(resultSet.getInt(2)))
                    .withName(resultSet.getString(3))
                    .withSurname(resultSet.getString(4))
                    .withEmail(resultSet.getString(5))
                    .withPhone(resultSet.getString(6))
                    .withDocumentId(resultSet.getString(7))
                    .withAddress(resultSet.getString(8))
                    .withDriveLicenseNumber(resultSet.getString(9))
                    .withBalance(resultSet.getDouble(10))
                    .withStatus(resultSet.getInt(11))
                    .build();
            result.add(account);
        }
        statement.close();
        resultSet.close();
        return result;
    }

    private Account findAccountById(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_ACCOUNT_BY_ID);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Account account;
        if (resultSet.next()) {
            account = new Account.Builder()
                    .withId(resultSet.getInt(1))
                    .withRole(Role.getById(resultSet.getInt(2)))
                    .withName(resultSet.getString(3))
                    .withSurname(resultSet.getString(4))
                    .withEmail(resultSet.getString(5))
                    .withPhone(resultSet.getString(6))
                    .withDocumentId(resultSet.getString(7))
                    .withAddress(resultSet.getString(8))
                    .withDriveLicenseNumber(resultSet.getString(9))
                    .withBalance(resultSet.getDouble(10))
                    .withStatus(resultSet.getInt(11))
                    .build();
        } else {
            account = null;
        }
        statement.close();
        resultSet.close();
        return account;
    }

    public Account findAccountByEmail(String email, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_ACCOUNT_BY_EMAIL);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        Account account;
        if (resultSet.next()) {
            account = new Account.Builder()
                    .withId(resultSet.getInt(1))
                    .withRole(Role.getById(resultSet.getInt(2)))
                    .withName(resultSet.getString(3))
                    .withSurname(resultSet.getString(4))
                    .withEmail(resultSet.getString(5))
                    .withPhone(resultSet.getString(6))
                    .withDocumentId(resultSet.getString(7))
                    .withAddress(resultSet.getString(8))
                    .withDriveLicenseNumber(resultSet.getString(9))
                    .withBalance(resultSet.getDouble(10))
                    .withStatus(resultSet.getInt(11))
                    .build();
        } else {
            account = null;
        }
        statement.close();
        resultSet.close();
        return account;
    }
}

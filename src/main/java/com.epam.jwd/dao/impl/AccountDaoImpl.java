package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.model.account.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountDaoImpl implements Dao<Account, Integer> {
    private static final Logger logger = LogManager.getLogger(AccountDaoImpl.class);

    private static final String SQL_SAVE_ACCOUNT = "INSERT INTO account (role_id, name, surname, email, phone, document_id, address, drive_license_number, balance, status) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_ACCOUNT_BY_ID = "UPDATE account SET role_id = ?, name = ?, surname = ?, email = ?, phone = ?, document_id = ?, address = ?, drive_license_number = ?, balance = ?, status = ? WHERE id = ?";
    private static final String SQL_DELETE_ACCOUNT_BY_ID = "DELETE FROM account WHERE id = ?";
    private static final String SQL_FIND_ALL_ACCOUNTS = "SELECT id, role_id, name, surname, email, phone, document_id, address, drive_license_number, balance, status FROM account";
    private static final String SQL_FIND_ACCOUNT_BY_ID = "SELECT id, role_id, name, surname, email, phone, document_id, address, drive_license_number, balance, status FROM account WHERE id = ?";
    private static final String SQL_FIND_ACCOUNT_BY_EMAIL = "SELECT id, role_id, name, surname, email, phone, document_id, address, drive_license_number, balance, status FROM account WHERE email = ?";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Account save(Account account) {
        return null;
    }
//    public Account save(Account account) {
//        logger.info("save method " + AccountDaoImpl.class);
//        Connection connection = connectionPool.takeConnection();
//        try {
//            return saveAccount(account, connection);
//        } catch (SQLException throwables) {
//            logger.error(throwables);
//            return null;
//        } finally {
//            connectionPool.returnConnection(connection);
//        }
//    }

    @Override
    public Boolean update(Account account) {
//        logger.info("update method " + AccountDaoImpl.class);
//        Connection connection = connectionPool.takeConnection();
//        try {
//            return updateAccountById(account, connection);
//        } catch (SQLException throwables) {
//            logger.error(throwables);
//            return false;
//        } finally {
//            connectionPool.returnConnection(connection);
//        }
        return null;
    }

    @Override
    public Boolean delete(Account account) {
//        logger.info("delete method " + AccountDaoImpl.class);
//        Connection connection = connectionPool.takeConnection();
//        try {
//            return deleteAccountById(account.getId(), connection);
//        } catch (SQLException throwables) {
//            logger.error(throwables);
//            return false;
//        } finally {
//            connectionPool.returnConnection(connection);
//        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        logger.info("find all method " + AccountDaoImpl.class);
        List<Account> accounts = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        try {
            accounts = findAllAccounts(connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return accounts;
    }

    @Override
    public Account findById(Integer id) {
        logger.info("find by id method " + AccountDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        Account account = null;
        try {
            account = findAccountById(id, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return account;
    }

    public Account findByEmail(String email) {
        logger.info("find by email method " + AccountDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        Account account = null;
        try {
            account = findAccountByEmail(email, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return account;
    }

    public Account saveAccount(Account account, Connection connection) throws SQLException {
        logger.info("save method " + AccountDaoImpl.class);
        PreparedStatement statement = connection.prepareStatement(SQL_SAVE_ACCOUNT, new String[] {"id"});
        statement.setInt(1, account.getRoleId());
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

    public Boolean updateAccountById(Account account, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_BY_ID);
        statement.setInt(1, account.getRoleId());
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

    public Boolean deleteAccountById(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ACCOUNT_BY_ID);
        statement.setInt(1, id);
        Boolean result = Objects.equals(statement.executeUpdate(), 1);
        statement.close();
        return result;
    }

    private List<Account> findAllAccounts(Connection connection) throws SQLException {
        List<Account> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_ACCOUNTS);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Account account = new Account.Builder()
                    .withId(resultSet.getInt(1))
                    .withRoleId(resultSet.getInt(2))
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
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_ID);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Account account;
        if (resultSet.next()) {
            account = new Account.Builder()
                    .withId(resultSet.getInt(1))
                    .withRoleId(resultSet.getInt(2))
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
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_EMAIL);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        Account account;
        if (resultSet.next()) {
            account = new Account.Builder()
                    .withId(resultSet.getInt(1))
                    .withRoleId(resultSet.getInt(2))
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

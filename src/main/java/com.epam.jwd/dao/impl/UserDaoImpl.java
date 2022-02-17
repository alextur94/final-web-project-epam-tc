package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.api.Message;
import com.epam.jwd.dao.connectionpool.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.dao.sql.SqlQueries;
import com.epam.jwd.service.cript.Encoder;
import com.epam.jwd.service.dto.AbstractDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDaoImpl extends AbstractDto<Integer> implements Dao<User, Integer> {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    private final AccountDaoImpl accountDao = new AccountDaoImpl();

    @Override
    public User save(User user) throws DaoException {
        logger.info("save method " + UserDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return saveUser(user, connection);
        } catch (SQLException throwables) {
            logger.error(Message.SAVE_USER_ERROR, throwables);
            throw new DaoException(Message.SAVE_USER_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(User user) throws DaoException {
        logger.info("update method " + UserDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            String oldPass = findById(user.getId()).getPassword();
            if (equalsPasswords(oldPass, user.getPassword())) {
                user.setPassword(criptPassword(user.getPassword()));
            }
            return updateUserById(user, connection);
        } catch (SQLException throwables) {
            logger.error(Message.UPDATE_USER_ERROR, throwables);
            throw new DaoException(Message.UPDATE_USER_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(User user) throws DaoException {
        logger.info("delete method " + UserDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return deleteUserById(user.getId(), connection);
        } catch (SQLException throwables) {
            logger.error(Message.DELETE_USER_ERROR, throwables);
            throw new DaoException(Message.DELETE_USER_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public User findById(Integer id) throws DaoException {
        logger.info("find by id method " + UserDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        User user;
        try {
            user = findUserById(id, connection);
            if (user != null) {
                return user;
            }
            logger.error(Message.FIND_BY_ID_USER_ERROR);
            throw new DaoException(Message.FIND_BY_ID_USER_ERROR);
        } catch (SQLException throwables) {
            logger.error(Message.FIND_BY_ID_USER_ERROR, throwables);
            throw new DaoException(Message.FIND_BY_ID_USER_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        logger.info("find all method " + UserDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return findAllUsers(connection);
        } catch (SQLException throwables) {
            logger.error(Message.FIND_ALL_USERS_ERROR, throwables);
            throw new DaoException(Message.FIND_ALL_USERS_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public User findByLogin(String login) throws DaoException {
        logger.info("find by login method " + UserDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        User user;
        try {
            user = findUserByLogin(login, connection);
             if (user != null) {
                return user;
            }
            logger.error(Message.FIND_BY_LOGIN_ERROR);
            throw new DaoException(Message.FIND_BY_LOGIN_ERROR);
        } catch (SQLException throwables) {
            logger.error(Message.FIND_BY_LOGIN_ERROR, throwables);
            throw new DaoException(Message.FIND_BY_LOGIN_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public User findByLoginForUpdate(String login) throws DaoException {
        logger.info("find by login for update method " + UserDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return findUserByLogin(login, connection);
        } catch (SQLException throwables) {
            logger.error(Message.FIND_BY_LOGIN_ERROR, throwables);
            throw new DaoException(Message.FIND_BY_LOGIN_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public Boolean savePerson(User user, Account account) throws DaoException, SQLException {
        logger.info("save method " + UserDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            new AccountDaoImpl().saveAccount(account, connection);
            user.setAccountId(account.getId());
            saveUser(user, connection);
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException throwables) {
            connection.rollback();
            logger.error(Message.SAVE_PERSON_ERROR, throwables);
            throw new DaoException(Message.SAVE_PERSON_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public Boolean updateUserAccount(User user, Account account) throws DaoException, SQLException {
        logger.info("update user and account method " + UserDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            updateUserById(user, connection);
            accountDao.updateAccountById(account, connection);
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException throwables) {
            connection.rollback();
            logger.error(Message.UPDATE_PERSON_ERROR, throwables);
            throw new DaoException(Message.UPDATE_PERSON_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public User checkLoginPassword(String login, String password) throws DaoException {
        logger.info("check login and password on correct method " + UserDaoImpl.class);
        User findUser = findByLogin(login);
        if (findUser != null) {
            String checkPass = criptPassword(password);
            if (checkPass.equals(findUser.getPassword())) {
                return findUser;
            }
        }
        logger.error(Message.INCORRECT_LOGIN_ERROR);
        throw new DaoException(Message.INCORRECT_LOGIN_ERROR);
    }

    public User saveUser(User user, Connection connection) throws SQLException, DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_SAVE_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, criptPassword(user.getPassword()));
            statement.setInt(3, user.getAccountId());
            statement.executeUpdate();
        }
        return user;
    }

    private Boolean updateUserById(User user, Connection connection) throws SQLException {
        Boolean result;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_UPDATE_USER_BY_ID)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getAccountId());
            statement.setInt(4, user.getId());
            result = Objects.equals(statement.executeUpdate(), 1);
        }
        return result;
    }

    private Boolean deleteUserById(Integer id, Connection connection) throws SQLException {
        Boolean result;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_DELETE_USER_BY_ID)) {
            statement.setInt(1, id);
            result = Objects.equals(statement.executeUpdate(), 1);
        }
        return result;
    }

    private List<User> findAllUsers(Connection connection) throws SQLException {
        List<User> result = new ArrayList<>();
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_ALL_USERS)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4)
                );
                result.add(user);
            }
        }
        resultSet.close();
        return result;
    }

    private User findUserById(Integer id, Connection connection) throws SQLException {
        ResultSet resultSet;
        User user;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_USER_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4));
            } else {
                user = null;
            }
        }
        resultSet.close();
        return user;
    }

    private User findUserByLogin(String login, Connection connection) throws SQLException {
        ResultSet resultSet;
        User user;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_USER_BY_LOGIN)) {
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4));
            } else {
                user = null;
            }
        }
        resultSet.close();
        return user;
    }

    public String criptPassword(String password) throws DaoException {
        try {
            if (password == null) {
                logger.error("Password null");
                throw new DaoException("Password null");
            }
            return Encoder.hashPass(password);
        } catch (IllegalBlockSizeException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            logger.error(Message.ENCRYPT_PASSWORD_ERROR);
            throw new DaoException(Message.ENCRYPT_PASSWORD_ERROR);
        }
    }

    private Boolean equalsPasswords(String oldPass, String newPass) {
        return oldPass.equals(newPass);
    }
}

package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;

//import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.dao.sql.InsetSql;
import com.epam.jwd.service.cript.Encoder;
import com.epam.jwd.service.dto.AbstractDto;
import com.epam.jwd.service.dto.UserDto;
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
    private Dao<Account, Integer> dao = new AccountDaoImpl();

    private static final String SQL_SAVE_USER = "INSERT INTO user (login, password, account_id) VALUES (?,?,?)";
    private static final String SQL_UPDATE_USER_BY_ID = "UPDATE user SET login = ?, password = ?, account_id = ? WHERE id = ?";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM user WHERE id = ?";
    private static final String SQL_FIND_USER_BY_ID = "SELECT id, login, password, account_id FROM user WHERE id = ?";
    private static final String SQL_FIND_ALL_USERS = "SELECT id, login, password, account_id FROM user";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT id, login, password, account_id FROM user WHERE login = ?";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public User save(User user) throws SQLException {
        logger.info("save method " + UserDaoImpl.class);
        Connection connection = connectionPool.requestConnection();
        try {
            return saveUser(user, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
            return null;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(User user) {
        logger.info("update method " + UserDaoImpl.class);
        Connection connection = connectionPool.requestConnection();
        try {
            String oldPass = findById(user.getId()).getPassword();
            if (oldPass != user.getPassword()){
                user.setPassword(criptPassword(user.getPassword()));
            }
            return updateUserById(user, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(User user) {
        logger.info("delete method " + UserDaoImpl.class);
        Connection connection = connectionPool.requestConnection();
        try {
            return deleteUserById(user.getId(), connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public User findById(Integer id) {
        logger.info("find by id method " + UserDaoImpl.class);
        Connection connection = connectionPool.requestConnection();
        User user = null;
        try {
            user = findUserById(id, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        logger.info("find all method " + UserDaoImpl.class);
        List<User> users = new ArrayList<>();
        Connection connection = connectionPool.requestConnection();
        try {
            users = findAllUsers(connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    public Boolean savePerson(User user, Account account) {
        logger.info("save method " + UserDaoImpl.class);
        Connection connection = connectionPool.requestConnection();
        try {
            connection.setAutoCommit(false);
            new AccountDaoImpl().saveAccount(account, connection);
            user.setAccountId(account.getId());
            saveUser(user, connection);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return null;
    }

    public Boolean updatePerson(User user, Account account) {
        logger.info("update method " + UserDaoImpl.class);
        Connection connection = connectionPool.requestConnection();
        try {
            connection.setAutoCommit(false);
            String oldPass = findById(user.getId()).getPassword();
            if (oldPass != user.getPassword()){
                user.setPassword(criptPassword(user.getPassword()));
            }
            updateUserById(user, connection);
            ((AccountDaoImpl)dao).updateAccountById(account, connection);
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException throwables) {
            logger.error(throwables);
            throw new DaoException(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public User saveUser(User user, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_SAVE_USER, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getLogin());
        statement.setString(2, criptPassword(user.getPassword()));
        statement.setInt(3, user.getAccountId());
        statement.executeUpdate();
        statement.close();
        return user;
    }

    public User findByLogin(String login) {
        logger.info("find by login method " + UserDaoImpl.class);
//        Connection connection = connectionPool.takeConnection();
        Connection connection = connectionPool.requestConnection();
        User user = null;
        try {
            user = findUserByLogin(login, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    private Boolean updateUserById(User user, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_BY_ID);
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getPassword());
        statement.setInt(3, user.getAccountId());
        statement.setInt(4, user.getId());
        Boolean result = Objects.equals(statement.executeUpdate(), 1);
        statement.close();
        return result;
    }

    private Boolean deleteUserById(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID);
        statement.setInt(1, id);
        Boolean result = Objects.equals(statement.executeUpdate(), 1);
        statement.close();
        return result;
    }

    private List<User> findAllUsers(Connection connection) throws SQLException {
        List<User> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USERS);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = new User(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            );
            result.add(user);
        }
        statement.close();
        resultSet.close();
        return result;
    }

    private User findUserById(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        User user;
        if (resultSet.next()) {
            user = new User(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4));
        } else {
            user = null;
        }
        statement.close();
        resultSet.close();
        return user;
    }

    private User findUserByLogin(String login, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();
        User user;
        if (resultSet.next()) {
            user = new User(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4));
        } else {
            user = null;
        }
        statement.close();
        resultSet.close();
        return user;
    }

    public User checkLoginPassword(String login, String password){
        User findUser = findByLogin(login);
        if (findUser != null) {
            String checkPass = criptPassword(password);
            if(checkPass.equals(findUser.getPassword())){
                return findUser;
            }
        }
        return null;
    }

    public Integer getRole(UserDto userDto){
        return dao.findById(userDto.getAccountId()).getRole().getRoleId();
    }

    private synchronized String criptPassword(String password){
        try {
            return Encoder.hashPass(password);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

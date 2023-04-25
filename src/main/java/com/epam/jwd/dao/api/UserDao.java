package com.epam.jwd.dao.api;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.user.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDao extends Dao<User, Integer> {
    User findByLogin(String login) throws DaoException;
    User findByLoginForUpdate(String login) throws DaoException;
    Boolean savePerson(User user, Account account) throws DaoException;
    Boolean updateUserAccount(User user, Account account) throws DaoException;
    User checkLoginPassword(String login, String password) throws DaoException;
    User saveUser(User user, Connection connection) throws DaoException, SQLException;
    String criptPassword(String password) throws DaoException;
}

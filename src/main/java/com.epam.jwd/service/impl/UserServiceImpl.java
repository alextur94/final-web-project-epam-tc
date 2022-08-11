package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.AccountDao;
import com.epam.jwd.dao.api.UserDao;
import com.epam.jwd.dao.api.Message;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.AccountDaoImpl;
import com.epam.jwd.dao.impl.UserDaoImpl;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.impl.AccountConverter;
import com.epam.jwd.service.converter.impl.UserConverter;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.impl.AccountValidator;
import com.epam.jwd.service.validator.impl.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements Service<UserDto, Integer> {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private UserDao userDao = new UserDaoImpl();
    private AccountDao accountDao = new AccountDaoImpl();
    private UserConverter converterUser = new UserConverter();
    private AccountConverter converterAccount = new AccountConverter();
    private UserValidator validatorUser = new UserValidator();
    private AccountValidator validatorAccount = new AccountValidator();


    /**
     * Checking for the validity of the login, converting it and sending it for saving
     *
     * @param userDto entity
     * @return userdto
     */
    @Override
    public UserDto create(UserDto userDto) throws ServiceException, DaoException {
        logger.info("save method " + UserServiceImpl.class);
        validatorUser.validate(userDto);
        User check = userDao.findByLogin(userDto.getLogin());
        validatorUser.validateLoginUnique(check);
        User user = converterUser.convert(userDto);
        return converterUser.convert(userDao.save(user));
    }

    /**
     * Converting and sending for renewal
     *
     * @param userDto
     * @return the boolean
     */
    @Override
    public Boolean update(UserDto userDto) throws ServiceException, DaoException {
        logger.info("update method " + UserServiceImpl.class);
        User user = converterUser.convert(userDto);
        return userDao.update(user);
    }

    @Override
    public Boolean delete(UserDto userDto) throws ServiceException {
        return false;
    }

    /**
     * Converting and getting an entity by id
     *
     * @param id
     * @return userDto
     */
    @Override
    public UserDto getById(Integer id) throws ServiceException, DaoException {
        logger.info("get by id method " + UserServiceImpl.class);
        User user = userDao.findById(id);
        return converterUser.convert(user);
    }

    /**
     * Converting and getting all entities
     *
     * @return list userDto
     */
    @Override
    public List<UserDto> getAll() throws DaoException, ServiceException {
        logger.info("get all method " + UserServiceImpl.class);
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : userDao.findAll()) {
            usersDto.add(converterUser.convert(user));
        }
        return usersDto;
    }

    /**
     * Converting and getting a user by login
     *
     * @param login
     * @return userDto
     */
    public User getByLogin(String login) throws ServiceException, DaoException {
        logger.info("get login method " + UserServiceImpl.class);
        validatorUser.validateLogin(login);
        User user = userDao.findByLogin(login);
        return user;
    }

    /**
     * Checking if the given login is already in the database
     *
     * @param login
     * @return user
     */
    public User getByLoginForUpdate(String login) throws ServiceException, DaoException {
        logger.info("get login method " + UserServiceImpl.class);
        validatorUser.validateLogin(login);
        User user = userDao.findByLoginForUpdate(login);
        if (user == null) {
            return null;
        } else {
            throw new ServiceException(Message.FIND_BY_LOGIN_FOR_UPDATE_ERROR);
        }
    }

    /**
     * Saving the user. Check for uniqueness of login,
     * check for uniqueness of mail, convert and send data for saving
     *
     * @param userDto, accountDto
     */
    public void savePerson(UserDto userDto, AccountDto accountDto) throws ServiceException, DaoException {
        logger.info("create method " + UserServiceImpl.class);
        validatorUser.validate(userDto);
        User checkUser = userDao.findByLoginForUpdate(userDto.getLogin());
        validatorUser.validateLoginUnique(checkUser);
        validatorAccount.validate(accountDto);
        Account checkAccount = accountDao.findByEmail(accountDto.getEmail());
        validatorAccount.validateEmailUnique((checkAccount));
        User user = converterUser.convert(userDto);
        Account account = converterAccount.convert(accountDto);
        userDao.savePerson(user, account);
    }

    /**
     * Checking for the correctness of the password
     *
     * @param id, password
     * @return userDto
     */
    public Boolean checkPasswordOnCorrect(Integer id, String password) throws DaoException {
        User user = userDao.findById(id);
        String pass = userDao.criptPassword(password);
        return pass.equals(user.getPassword());
    }

    /**
     * Checking for the correctness of the login and password
     *
     * @param login, password
     * @return userDto
     */
    public UserDto checkLoginPassword(String login, String password) throws DaoException, ServiceException {
        User user = userDao.checkLoginPassword(login, password);
        return converterUser.convert(user);
    }

    /**
     * Password encryption and attempt to change it
     *
     * @param userId, password
     */
    public void changePassword(Integer userId, String password) throws DaoException {
        User user = userDao.findById(userId);
        user.setPassword(userDao.criptPassword(password));
        userDao.update(user);
    }
}

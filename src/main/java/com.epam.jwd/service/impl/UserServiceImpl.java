package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.AccountDao;
import com.epam.jwd.dao.api.Message;
import com.epam.jwd.dao.api.UserDao;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.AccountDaoImpl;
import com.epam.jwd.dao.impl.UserDaoImpl;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.api.UserService;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.AccountConverterImpl;
import com.epam.jwd.service.converter.impl.UserConverterImpl;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.AccountValidator;
import com.epam.jwd.service.validator.api.UserValidator;
import com.epam.jwd.service.validator.impl.AccountValidatorImpl;
import com.epam.jwd.service.validator.impl.UserValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private UserDao userDao = new UserDaoImpl();
    private AccountDao accountDao = new AccountDaoImpl();
    private UserValidator validatorUser = new UserValidatorImpl();
    private AccountValidator validatorAccount = new AccountValidatorImpl();
    private Converter<User, UserDto, Integer> converterUser = new UserConverterImpl();
    private Converter<Account, AccountDto, Integer> converterAccount = new AccountConverterImpl();

    @Override
    public UserDto create(UserDto userDto) throws ServiceException {
        return null;
    }

    /**
     * Converting and sending for renewal
     *
     * @param userDto
     * @return the boolean
     */
    @Override
    public Boolean update(UserDto userDto) throws ServiceException {
        logger.info("update method " + UserServiceImpl.class);
        User user = converterUser.convert(userDto);
        try {
            return userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
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
    public UserDto getById(Integer id) throws ServiceException {
        logger.info("get by id method " + UserServiceImpl.class);
        User user = null;
        try {
            user = userDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return converterUser.convert(user);
    }

    /**
     * Converting and getting all entities
     *
     * @return list userDto
     */
    @Override
    public List<UserDto> getAll() throws ServiceException {
        logger.info("get all method " + UserServiceImpl.class);
        List<UserDto> usersDto = new ArrayList<>();
        try {
            for (User user : userDao.findAll()) {
                usersDto.add(converterUser.convert(user));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return usersDto;
    }

    /**
     * Converting and getting a user by login
     *
     * @param login
     * @return userDto
     */
    @Override
    public User getByLogin(String login) throws ServiceException {
        logger.info("get login method " + UserServiceImpl.class);
        validatorUser.validateLogin(login);
        User user = null;
        try {
            user = userDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    /**
     * Checking if the given login is already in the database
     *
     * @param login
     * @return user
     */
    @Override
    public User getByLoginForUpdate(String login) throws ServiceException {
        logger.info("get login method " + UserServiceImpl.class);
        validatorUser.validateLogin(login);
        User user = null;
        try {
            user = userDao.findByLoginForUpdate(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
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
    @Override
    public void savePerson(UserDto userDto, AccountDto accountDto) throws ServiceException {
        logger.info("create method " + UserServiceImpl.class);
        try {
            validatorUser.validate(userDto);
            User checkUser = userDao.findByLoginForUpdate(userDto.getLogin()); //1
            validatorUser.validateLoginUnique(checkUser);
            validatorAccount.validate(accountDto);
            Account checkAccount = accountDao.findByEmail(accountDto.getEmail()); //2
            validatorAccount.validateEmailUnique((checkAccount));
            User user = converterUser.convert(userDto);
            Account account = converterAccount.convert(accountDto);
            userDao.savePerson(user, account); //3
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Checking for the correctness of the password
     *
     * @param id, password
     * @return userDto
     */
    @Override
    public Boolean checkPasswordOnCorrect(Integer id, String password) throws ServiceException {
        User user = null;
        try {
            user = userDao.findById(id);
            String pass = userDao.criptPassword(password);
            return pass.equals(user.getPassword());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Checking for the correctness of the login and password
     *
     * @param login, password
     * @return userDto
     */
    @Override
    public UserDto checkLoginPassword(String login, String password) throws ServiceException {
        User user = null;
        try {
            user = userDao.checkLoginPassword(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return converterUser.convert(user);
    }

    /**
     * Password encryption and attempt to change it
     *
     * @param userId, password
     */
    @Override
    public void changePassword(Integer userId, String password) throws ServiceException {
        User user = null;
        try {
            user = userDao.findById(userId);
            user.setPassword(userDao.criptPassword(password));
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}

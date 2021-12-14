package com.epam.jwd.service.impl;

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
import com.epam.jwd.service.exception.ValidateException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.AccountValidator;
import com.epam.jwd.service.validator.impl.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserServiceImpl implements Service<UserDto, Integer> {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserDaoImpl daoUser = new UserDaoImpl();
    private final AccountDaoImpl daoAccount = new AccountDaoImpl();
    private final UserConverter converterUser = new UserConverter();
    private final AccountConverter converterAccount = new AccountConverter();
    private final UserValidator validatorUser = new UserValidator();
    private final AccountValidator validatorAccount = new AccountValidator();

    @Override
    public UserDto create(UserDto userDto) throws ServiceException, SQLException {
        logger.info("save method " + UserServiceImpl.class);
        validatorUser.validate(userDto);
        User check = daoUser.findByLogin(userDto.getLogin());
        validatorUser.validateLoginUnique(check);
        User user = converterUser.convert(userDto);
        return converterUser.convert(daoUser.save(user));
    }

    @Override
    public Boolean update(UserDto userDto) throws ServiceException {
        logger.info("update method " + UserServiceImpl.class);
        try {
            User user = converterUser.convert(userDto);
            return daoUser.update(user);
        }catch (Exception e){
            logger.info("update method " + UserServiceImpl.class);
            throw new ServiceException(ValidateException.UNKNOWN_EXCEPTION);
        }
    }

    @Override
    public Boolean delete(UserDto userDto) throws ServiceException {
        return false;
    }

    @Override
    public UserDto getById(Integer id) throws ServiceException {
        logger.info("get by id method " + UserServiceImpl.class);
        User user = daoUser.findById(id);
        return converterUser.convert(user);
    }

    @Override
    public List<UserDto> getAll() {
        logger.info("get all method " + UserServiceImpl.class);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : daoUser.findAll()) {
            userDtos.add(converterUser.convert(user));
        }
        return userDtos;
    }

    public UserDto getByLogin(String login) throws ServiceException {
        logger.info("get login method " + UserServiceImpl.class);
        validatorUser.validateLogin(login);
        User user = daoUser.findByLogin(login);
        if (Objects.isNull(user)){
            throw new ServiceException(ValidateException.USER_NOT_FOUND);
        }
        return converterUser.convert(user);
    }

    public void savePerson(UserDto userDto, AccountDto accountDto) throws ServiceException {
        logger.info("create method " + UserServiceImpl.class);
        validatorUser.validate(userDto);
        User checkUser = daoUser.findByLogin(userDto.getLogin());
        validatorUser.validateLoginUnique(checkUser);
        validatorAccount.validate(accountDto);
        Account checkAccount = daoAccount.findByEmail(accountDto.getEmail());
        validatorAccount.validateEmailUnique((checkAccount));
        User user = converterUser.convert(userDto);
        Account account = converterAccount.convert(accountDto);
        daoUser.savePerson(user, account);
    }

    public Boolean updatePerson(UserDto userDto, AccountDto accountDto){
        logger.info("update method " + UserServiceImpl.class);
        User user = converterUser.convert(userDto);
        Account account = converterAccount.convert(accountDto);
        daoUser.updatePerson(user, account);
        return true;
    }

    public UserDto checkLoginPassword(String login ,String password) throws ServiceException {
        User user = daoUser.checkLoginPassword(login, password);
        return converterUser.convert(user);
    }
}

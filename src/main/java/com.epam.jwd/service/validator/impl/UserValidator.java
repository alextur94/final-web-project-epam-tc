package com.epam.jwd.service.validator.impl;

import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.exception.ValidateException;
import com.epam.jwd.service.validator.api.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class UserValidator implements Validator<UserDto, Integer> {
    private static final Logger logger = LogManager.getLogger(UserValidator.class);

    private static final Integer LOGIN_MIN_LENGTH = 2;
    private static final Integer LOGIN_MAX_LENGTH = 20;
    private static final Integer PASSWORD_MIN_LENGTH = 4;

    @Override
    public void validate(UserDto userDto) throws ServiceException {
        if (Objects.isNull(userDto)) {
            logger.info(ValidateException.USER_IS_EMPTY + UserValidator.class);
            throw new ServiceException(ValidateException.USER_IS_EMPTY);
        }
        validateLogin(userDto.getLogin());
        validatePassword(userDto.getPassword());
    }

    public void validateLogin(String login) throws ServiceException {
        if (Objects.isNull(login)
                || login.length() < LOGIN_MIN_LENGTH
                || login.length() > LOGIN_MAX_LENGTH) {
            logger.info(ValidateException.LOGIN + UserValidator.class);
            throw new ServiceException(ValidateException.LOGIN);
        }
    }

    public void validatePassword(String password) throws ServiceException {
        if (Objects.isNull(password)
                || password.length() < PASSWORD_MIN_LENGTH) {
            throw new ServiceException(ValidateException.PASSWORD);
        }
    }

    public void validateLoginUnique(User user) throws ServiceException {
        if (!Objects.isNull(user)) {
            throw new ServiceException(ValidateException.USER_IS_NOT_UNIQUE);
        }
    }
}

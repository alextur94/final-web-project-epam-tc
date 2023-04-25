package com.epam.jwd.service.validator.impl;

import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.exception.ValidateException;
import com.epam.jwd.service.validator.api.UserValidator;
import com.epam.jwd.service.validator.api.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class UserValidatorImpl implements UserValidator {
    private static final Logger logger = LogManager.getLogger(UserValidatorImpl.class);
    private static final Integer LOGIN_MIN_LENGTH = 3;
    private static final Integer LOGIN_MAX_LENGTH = 20;
    private static final Integer PASSWORD_MIN_LENGTH = 5;
    private static final Integer PASSWORD_MAX_LENGTH = 25;

    /**
     * Checking that the user is there
     *
     * @param userDto
     */
    @Override
    public void validate(UserDto userDto) throws ServiceException {
        if (Objects.isNull(userDto)) {
            logger.info(ValidateException.USER_IS_EMPTY + UserValidatorImpl.class);
            throw new ServiceException(ValidateException.USER_IS_EMPTY);
        }
        validateLogin(userDto.getLogin());
        validatePassword(userDto.getPassword());
    }

    /**
     * Checking the login that it is not less than the minimum length and not more than the maximum
     *
     * @param login
     */
    @Override
    public void validateLogin(String login) throws ServiceException {
        if (Objects.isNull(login)
                || login.length() <= LOGIN_MIN_LENGTH
                || login.length() > LOGIN_MAX_LENGTH) {
            logger.info(ValidateException.LOGIN + UserValidatorImpl.class);
            throw new ServiceException(ValidateException.LOGIN);
        }
    }

    /**
     * Login check for uniqueness
     *
     * @param user
     */
    @Override
    public void validateLoginUnique(User user) throws ServiceException {
        if (!Objects.isNull(user)) {
            throw new ServiceException(ValidateException.USER_IS_NOT_UNIQUE);
        }
    }

    /**
     * Password checking that it is not less than the minimum length and not more than the maximum
     *
     * @param password
     */
    @Override
    public void validatePassword(String password) throws ServiceException {
        if (Objects.isNull(password)
                || password.length() < PASSWORD_MIN_LENGTH
                || password.length() > PASSWORD_MAX_LENGTH) {
            throw new ServiceException(ValidateException.PASSWORD);
        }
    }

    /**
     * Password checking that it is not less than the minimum length and not more than the maximum
     *
     * @param firstPass, secondPass
     */
    @Override
    public void validateRepeatPassword(String firstPass, String secondPass) throws ServiceException {
        if (!firstPass.equals(secondPass)) {
            throw new ServiceException(ValidateException.REPEAT_PASSWORD);
        }
    }
}

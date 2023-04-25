package com.epam.jwd.service.validator.api;

import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;

public interface UserValidator extends Validator<UserDto, Integer> {
    void validateLogin(String login) throws ServiceException;
    void validateLoginUnique(User user) throws ServiceException;
    void validatePassword(String password) throws ServiceException;
    void validateRepeatPassword(String firstPass, String secondPass) throws ServiceException;
}

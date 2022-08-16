package com.epam.jwd.service.api;

import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;

public interface UserService extends Service<UserDto, Integer> {
    User getByLogin(String login) throws ServiceException;
    User getByLoginForUpdate(String login) throws ServiceException;
    void savePerson(UserDto userDto, AccountDto accountDto) throws ServiceException;
    Boolean checkPasswordOnCorrect(Integer id, String password) throws ServiceException;
    UserDto checkLoginPassword(String login, String password) throws ServiceException;
    void changePassword(Integer userId, String password) throws ServiceException;
}

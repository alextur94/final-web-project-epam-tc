package com.epam.jwd.service.impl;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.UserDaoImpl;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.converter.impl.UserConverter;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    UserDaoImpl daoUser;
    @Mock
    UserConverter converterUser;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void createShouldReturnUser() throws ServiceException, DaoException {
        User user = getUser();
        UserDto userDto = getUserDto();
        when(daoUser.findByLogin(userDto.getLogin())).thenReturn(null);
        when(converterUser.convert(userDto)).thenReturn(user);
        when(daoUser.save(user)).thenReturn(user);
        when(converterUser.convert(user)).thenReturn(userDto);

        UserDto dto = userService.create(userDto);
        assertEquals(userDto, dto);
    }

    public UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setLogin("User");
        userDto.setPassword("Password");
        userDto.setAccountId(1);
        return userDto;
    }

    public User getUser() {
        User user = new User();
        user.setId(1);
        user.setLogin("User");
        user.setPassword("Password");
        user.setAccountId(1);
        return user;
    }
}
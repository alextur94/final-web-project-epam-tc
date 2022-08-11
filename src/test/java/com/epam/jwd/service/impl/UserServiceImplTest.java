package com.epam.jwd.service.impl;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.AccountDaoImpl;
import com.epam.jwd.dao.impl.UserDaoImpl;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.account.Role;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.converter.impl.AccountConverter;
import com.epam.jwd.service.converter.impl.UserConverter;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.impl.AccountValidator;
import com.epam.jwd.service.validator.impl.UserValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    UserDaoImpl userDao;
    @Mock
    AccountDaoImpl accountDao;
    @Mock
    UserConverter userConverter;
    @Mock
    AccountConverter accountConverter;
    @Mock
    UserValidator userValidator;
    @Mock
    AccountValidator accountValidator;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void createShouldReturnUser() throws ServiceException, DaoException, SQLException {
        User user = getUser();
        User userId = getUserId();
        UserDto userDto = getUserDto();
        UserDto userDtoId = getUserDtoId();

        doNothing().when(userValidator).validate(userDto);
        when(userDao.findByLogin(userDto.getLogin())).thenReturn(userId);
        doNothing().when(userValidator).validateLoginUnique(userId);
        when(userConverter.convert(userDto)).thenReturn(user);
        when(userDao.save(user)).thenReturn(userId);
        when(userConverter.convert(userId)).thenReturn(userDtoId);

        UserDto actualUserDto = userService.create(userDto);
        assertEquals(userDtoId, actualUserDto);
    }

    @Test
    public void updateShouldReturnTrue() throws DaoException, ServiceException {
        User userId = getUserId();
        UserDto userDtoId = getUserDtoId();

        when(userConverter.convert(userDtoId)).thenReturn(userId);
        when(userDao.update(userId)).thenReturn(true);

        Boolean actualResult = userService.update(userDtoId);
        assertTrue(actualResult);
    }

    @Test
    public void updateShouldReturnFalse() throws DaoException, ServiceException {
        User userId = getUserId();
        UserDto userDtoId = getUserDtoId();

        when(userConverter.convert(userDtoId)).thenReturn(userId);
        when(userDao.update(userId)).thenReturn(false);

        Boolean actualResult = userService.update(userDtoId);
        assertFalse(actualResult);
    }

    @Test
    public void getByIdShouldReturnUserDto() throws DaoException, ServiceException {
        User userId = getUserId();
        UserDto userDtoId = getUserDtoId();

        when(userDao.findById(0)).thenReturn(userId);
        when(userConverter.convert(userId)).thenReturn(userDtoId);

        UserDto actualUser = userService.getById(0);
        assertEquals(userDtoId, actualUser);
    }

    @Test
    public void getAllShouldReturnListUserDto() throws DaoException, ServiceException {
        User userId = getUserId();
        UserDto userDtoId = getUserDtoId();
        List<User> listUser = getAllUser();
        List<UserDto> listUserDto = getAllUserDto();

        when(userDao.findAll()).thenReturn(listUser);
        when(userConverter.convert(userId)).thenReturn(userDtoId);

        List<UserDto> actualList = userService.getAll();
        assertEquals(listUserDto, actualList);
    }

    @Test
    public void getByLoginShouldReturnUser() throws ServiceException, DaoException {
        String login = "login";
        User userId = getUserId();

        doNothing().when(userValidator).validateLogin(login);
        when(userDao.findByLogin(login)).thenReturn(userId);

        User actualUser = userService.getByLogin(login);
        assertEquals(userId, actualUser);
    }

    @Test
    public void getByLoginForUpdateShouldReturnNull() throws ServiceException, DaoException {
        User userId = getUserId();

        doNothing().when(userValidator).validateLogin(userId.getLogin());
        when(userDao.findByLoginForUpdate(userId.getLogin())).thenReturn(null);

        User actualUser = userService.getByLoginForUpdate(userId.getLogin());
        assertNull(actualUser);
    }

    @Test(expected = ServiceException.class)
    public void getByLoginForUpdateShouldThrowsException() throws ServiceException, DaoException {
        User userId = getUserId();

        when(userDao.findByLoginForUpdate(userId.getLogin())).thenReturn(userId);

        userService.getByLoginForUpdate(userId.getLogin());
    }

    @Test
    public void savePersonShouldSavePersonInDB() throws ServiceException, DaoException, SQLException {
        User user = getUser();
        UserDto userDto = getUserDto();
        Account account = getAccount();
        AccountDto accountDto = getAccountDto();

        doNothing().when(userValidator).validate(userDto);
        when(userDao.findByLoginForUpdate(userDto.getLogin())).thenReturn(user);
        doNothing().when(userValidator).validateLoginUnique(user);
        doNothing().when(accountValidator).validate(accountDto);
        when(accountDao.findByEmail(accountDto.getEmail())).thenReturn(account);
        doNothing().when(accountValidator).validateEmailUnique(account);
        when(userConverter.convert(userDto)).thenReturn(user);

        userService.savePerson(userDto, accountDto);
        verify(userDao, times(1)).savePerson(any(), any());
    }

    @Test
    public void checkPasswordOnCorrectShouldReturnTrue() throws DaoException {
        User user = getUserId();
        Integer id = 1;
        when(userDao.findById(id)).thenReturn(user);
        when(userDao.criptPassword(user.getPassword())).thenReturn(user.getPassword());

        Boolean actualResult = userService.checkPasswordOnCorrect(id, user.getPassword());
        assertTrue(actualResult);
    }

    @Test
    public void checkPasswordOnCorrectShouldReturnFalse() throws DaoException {
        User user = getUserId();
        Integer id = 1;
        when(userDao.findById(id)).thenReturn(user);
        when(userDao.criptPassword(user.getPassword())).thenReturn("anotherPass");

        Boolean actualResult = userService.checkPasswordOnCorrect(id, user.getPassword());
        assertFalse(actualResult);
    }

    @Test
    public void checkLoginPasswordShouldReturnUserDto() throws DaoException, ServiceException {
        String login = "login";
        String pass = "pass";
        User user = getUserId();
        UserDto userDto = getUserDtoId();

        when(userDao.checkLoginPassword(login, pass)).thenReturn(user);
        when(userConverter.convert(user)).thenReturn(userDto);

        UserDto actualUser = userService.checkLoginPassword(login, pass);
        assertEquals(userDto, actualUser);
    }

    @Test
    public void changePasswordShouldUpdatePass() throws DaoException {
        Integer id = 0;
        String pass = "pass";
        User user = getUserId();

        when(userDao.findById(id)).thenReturn(user);
        when(userDao.criptPassword(pass)).thenReturn(user.getPassword());
        when(userDao.update(user)).thenReturn(true);

        userService.changePassword(id, pass);
        verify(userDao, times(1)).update(user);
    }

    private UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setLogin("Login");
        userDto.setPassword("Password");
        userDto.setAccountId(1);
        return userDto;
    }

    private UserDto getUserDtoId() {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setLogin("Login");
        userDto.setPassword("Password");
        userDto.setAccountId(1);
        return userDto;
    }

    private User getUser() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("Password");
        user.setAccountId(1);
        return user;
    }

    private User getUserId() {
        User user = new User();
        user.setId(1);
        user.setLogin("User");
        user.setPassword("Password");
        user.setAccountId(1);
        return user;
    }

    private List<User> getAllUser() {
        List<User> list = new ArrayList<>();
        list.add(getUserId());
        list.add(getUserId());
        list.add(getUserId());
        return list;
    }

    private List<UserDto> getAllUserDto() {
        List<UserDto> list = new ArrayList<>();
        list.add(getUserDtoId());
        list.add(getUserDtoId());
        list.add(getUserDtoId());
        return list;
    }

    private AccountDto getAccountDto() {
        return new AccountDto.Builder()
                .withRole(Role.USER)
                .withEmail("email@mail.com")
                .withBalance(0.00)
                .withStatus(0)
                .build();
    }

    private Account getAccount() {
        return new Account.Builder()
                .withRole(Role.USER)
                .withEmail("email@mail.com")
                .withBalance(0.00)
                .withStatus(0)
                .build();
    }
}
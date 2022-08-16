package com.epam.jwd.service.impl;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.AccountDaoImpl;
import com.epam.jwd.dao.impl.UserDaoImpl;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.account.Role;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.converter.impl.UserConverterImpl;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.impl.AccountValidatorImpl;
import com.epam.jwd.service.validator.impl.UserValidatorImpl;
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
    private UserDaoImpl userDao;
    @Mock
    private AccountDaoImpl accountDao;
    @Mock
    private UserConverterImpl userConverterImpl;
    @Mock
    private UserValidatorImpl userValidatorImpl;
    @Mock
    private AccountValidatorImpl accountValidatorImpl;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void createShouldReturnUser() throws ServiceException, DaoException {
        User user = createUser();
        User userId = createUserWithId();
        UserDto userDto = createUserDto();
        UserDto userDtoId = createUserDtoWithId();

        doNothing().when(userValidatorImpl).validate(userDto);
        doNothing().when(userValidatorImpl).validateLoginUnique(userId);
        when(userDao.findByLogin(userDto.getLogin())).thenReturn(userId);
        when(userConverterImpl.convert(userDto)).thenReturn(user);
        when(userDao.save(user)).thenReturn(userId);
        when(userConverterImpl.convert(userId)).thenReturn(userDtoId);

//        UserDto actualUserDto = userService.create(userDto);
//        assertEquals(userDtoId, actualUserDto);
    }

    @Test
    public void updateShouldReturnTrue() throws ServiceException, DaoException {
        User userId = createUserWithId();
        UserDto userDtoId = createUserDtoWithId();

        when(userConverterImpl.convert(userDtoId)).thenReturn(userId);
        when(userDao.update(userId)).thenReturn(true);

        Boolean actualResult = userService.update(userDtoId);
        assertTrue(actualResult);
    }

    @Test
    public void updateShouldReturnFalse() throws DaoException, ServiceException {
        User userId = createUserWithId();
        UserDto userDtoId = createUserDtoWithId();

        when(userConverterImpl.convert(userDtoId)).thenReturn(userId);
        when(userDao.update(userId)).thenReturn(false);

        Boolean actualResult = userService.update(userDtoId);
        assertFalse(actualResult);
    }

    @Test
    public void getByIdShouldReturnUserDto() throws DaoException, ServiceException {
        Integer id = 0;
        User userId = createUserWithId();
        UserDto userDtoId = createUserDtoWithId();

        when(userDao.findById(id)).thenReturn(userId);
        when(userConverterImpl.convert(userId)).thenReturn(userDtoId);

        UserDto actualUser = userService.getById(id);
        assertEquals(userDtoId, actualUser);
    }

    @Test
    public void getAllShouldReturnListUserDto() throws DaoException, ServiceException {
        User userId = createUserWithId();
        UserDto userDtoId = createUserDtoWithId();
        List<User> listUser = createListUsers();
        List<UserDto> listUserDto = createListUsersDro();

        when(userDao.findAll()).thenReturn(listUser);
        when(userConverterImpl.convert(userId)).thenReturn(userDtoId);

        List<UserDto> actualList = userService.getAll();
        assertEquals(listUserDto, actualList);
    }

    @Test
    public void getByLoginShouldReturnUser() throws ServiceException, DaoException {
        String login = "login";
        User userId = createUserWithId();

        doNothing().when(userValidatorImpl).validateLogin(login);
        when(userDao.findByLogin(login)).thenReturn(userId);

        User actualUser = userService.getByLogin(login);
        assertEquals(userId, actualUser);
    }

    @Test
    public void getByLoginForUpdateShouldReturnNull() throws ServiceException, DaoException {
        User userId = createUserWithId();

        doNothing().when(userValidatorImpl).validateLogin(userId.getLogin());
        when(userDao.findByLoginForUpdate(userId.getLogin())).thenReturn(null);

        User actualUser = userService.getByLoginForUpdate(userId.getLogin());
        assertNull(actualUser);
    }

    @Test(expected = ServiceException.class)
    public void getByLoginForUpdateShouldThrowsException() throws ServiceException, DaoException {
        User userId = createUserWithId();

        when(userDao.findByLoginForUpdate(userId.getLogin())).thenReturn(userId);

        userService.getByLoginForUpdate(userId.getLogin());
    }

    @Test
    public void savePersonShouldSavePersonInDB() throws ServiceException, DaoException, SQLException {
        User user = createUser();
        UserDto userDto = createUserDto();
        Account account = createAccount();
        AccountDto accountDto = createAccountDto();

        doNothing().when(userValidatorImpl).validate(userDto);
        doNothing().when(userValidatorImpl).validateLoginUnique(user);
        doNothing().when(accountValidatorImpl).validate(accountDto);
        doNothing().when(accountValidatorImpl).validateEmailUnique(account);
        when(userDao.findByLoginForUpdate(userDto.getLogin())).thenReturn(user);
        when(accountDao.findByEmail(accountDto.getEmail())).thenReturn(account);
        when(userConverterImpl.convert(userDto)).thenReturn(user);

        userService.savePerson(userDto, accountDto);
        verify(userDao, times(1)).savePerson(any(), any());
    }

    @Test
    public void checkPasswordOnCorrectShouldReturnTrue() throws ServiceException, DaoException {
        Integer id = 1;
        User user = createUserWithId();

        when(userDao.findById(id)).thenReturn(user);
        when(userDao.criptPassword(user.getPassword())).thenReturn(user.getPassword());

        Boolean actualResult = userService.checkPasswordOnCorrect(id, user.getPassword());
        assertTrue(actualResult);
    }

    @Test
    public void checkPasswordOnCorrectShouldReturnFalse() throws DaoException, ServiceException {
        Integer id = 1;
        User user = createUserWithId();

        when(userDao.findById(id)).thenReturn(user);
        when(userDao.criptPassword(user.getPassword())).thenReturn("anotherPass");

        Boolean actualResult = userService.checkPasswordOnCorrect(id, user.getPassword());
        assertFalse(actualResult);
    }

    @Test
    public void checkLoginPasswordShouldReturnUserDto() throws DaoException, ServiceException {
        String login = "login";
        String pass = "pass";
        User user = createUserWithId();
        UserDto userDto = createUserDtoWithId();

        when(userDao.checkLoginPassword(login, pass)).thenReturn(user);
        when(userConverterImpl.convert(user)).thenReturn(userDto);

        UserDto actualUser = userService.checkLoginPassword(login, pass);
        assertEquals(userDto, actualUser);
    }

    @Test
    public void changePasswordShouldUpdatePass() throws DaoException, ServiceException {
        Integer id = 0;
        String pass = "pass";
        User user = createUserWithId();

        when(userDao.findById(id)).thenReturn(user);
        when(userDao.criptPassword(pass)).thenReturn(user.getPassword());
        when(userDao.update(user)).thenReturn(true);

        userService.changePassword(id, pass);
        verify(userDao, times(1)).update(user);
    }

    private UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setLogin("Login");
        userDto.setPassword("Password");
        userDto.setAccountId(1);
        return userDto;
    }

    private UserDto createUserDtoWithId() {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setLogin("Login");
        userDto.setPassword("Password");
        userDto.setAccountId(1);
        return userDto;
    }

    private User createUser() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("Password");
        user.setAccountId(1);
        return user;
    }

    private User createUserWithId() {
        User user = new User();
        user.setId(1);
        user.setLogin("User");
        user.setPassword("Password");
        user.setAccountId(1);
        return user;
    }

    private List<User> createListUsers() {
        List<User> list = new ArrayList<>();
        list.add(createUserWithId());
        list.add(createUserWithId());
        list.add(createUserWithId());
        return list;
    }

    private List<UserDto> createListUsersDro() {
        List<UserDto> list = new ArrayList<>();
        list.add(createUserDtoWithId());
        list.add(createUserDtoWithId());
        list.add(createUserDtoWithId());
        return list;
    }

    private AccountDto createAccountDto() {
        return new AccountDto.Builder()
                .withRole(Role.USER)
                .withEmail("email@mail.com")
                .withBalance(0.00)
                .withStatus(0)
                .build();
    }

    private Account createAccount() {
        return new Account.Builder()
                .withRole(Role.USER)
                .withEmail("email@mail.com")
                .withBalance(0.00)
                .withStatus(0)
                .build();
    }
}

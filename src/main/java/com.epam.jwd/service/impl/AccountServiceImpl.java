package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.AccountDao;
import com.epam.jwd.dao.api.Message;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.AccountDaoImpl;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.order.Order;
import com.epam.jwd.dao.model.order.Status;
import com.epam.jwd.service.api.AccountService;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.AccountConverterImpl;
import com.epam.jwd.service.converter.impl.CarConverterImpl;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.AccountValidator;
import com.epam.jwd.service.validator.impl.AccountValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao = new AccountDaoImpl();
    private AccountValidator accountValidator = new AccountValidatorImpl();
    private Converter<Account, AccountDto, Integer> accountConverter = new AccountConverterImpl();
    private Converter<Car, CarDto, Integer> carConverter = new CarConverterImpl();
    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);
    private static final String EXCEPTION_NOT_BE_CLOSED_MESSAGE = "The order cannot be closed. ";
    private static final Double AMOUNT_IS_ZERO = 0.00;

    @Override
    public AccountDto create(AccountDto value) throws ServiceException {
        return null;
    }

    /**
     * Update entity Account
     *
     * @param accountDto
     * @return
     */
    @Override
    public Boolean update(AccountDto accountDto) throws ServiceException {
        logger.info("update method " + AccountServiceImpl.class);
        Account account = accountConverter.convert(accountDto);
        try {
            return accountDao.update(account);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Boolean delete(AccountDto value) throws ServiceException {
        return null;
    }

    /**
     * Get by id AccountDao
     *
     * @param id
     * @return AccountDao
     */
    @Override
    public AccountDto getById(Integer id) throws ServiceException {
        logger.info("get by id method " + AccountServiceImpl.class);
        Account account = null;
        try {
            account = accountDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return accountConverter.convert(account);
    }

    /**
     * Get all records
     *
     * @return List<AccountDao></>
     */
    @Override
    public List<AccountDto> getAll() throws ServiceException {
        logger.info("get all method " + AccountServiceImpl.class);
        List<AccountDto> accountDtos = new ArrayList<>();
        try {
            for (Account account : accountDao.findAll()) {
                accountDtos.add(accountConverter.convert(account));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return accountDtos;
    }

    /**
     * Found by email for ypdate
     *
     * @param email
     * @return the String
     */
    @Override
    public AccountDto getByEmailForUpdate(String email) throws ServiceException {
        logger.info("get by email for update method " + AccountServiceImpl.class);
        accountValidator.validateEmail(email);
        Account account = null;
        try {
            account = accountDao.findByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if (account == null) {
            return null;
        } else {
            throw new ServiceException(Message.FIND_BY_EMAIL_FOR_UPDATE_ERROR);
        }
    }

    /**
     * Transfer money from user account to admin account
     *
     * @param user and order entity
     * @return the boolean
     */
    @Override
    public Boolean transferAmountAccountAdmin(AccountDto user, Order order) throws ServiceException {
        logger.info("transfer from user to admin method " + AccountServiceImpl.class);
        Double accountBalance = user.getBalance();
        Double amount = order.getCurrentSum();
        if (accountBalance >= amount) {
            AccountDto admin = null;
            try {
                admin = accountConverter.convert(accountDao.findById(1));
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
            admin.setBalance(admin.getBalance() + amount);
            user.setBalance(user.getBalance() - amount);
            order.setStatus(Status.NEW.getId());
            order.setPaymentStatus((byte) 1);
            Account daoAdmin = accountConverter.convert(admin);
            Account daoUser = accountConverter.convert(user);
            try {
                return accountDao.saveTransactionUserAdmin(daoUser, daoAdmin, order);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return false;
    }

    /**
     * User cancellation of an order
     *
     * @param accountDto, carDto and order
     * @return the boolean
     */
    @Override
    public Boolean cancelOrder(AccountDto accountDto, CarDto carDto, Order order) throws ServiceException {
        logger.info("cancel order method " + AccountServiceImpl.class);
        Byte orderStatus = order.getStatus();
        AccountDto adminDto = null;
        try {
            adminDto = accountConverter.convert(accountDao.findById(1));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        Double amount = order.getCurrentSum();
        if (orderStatus > 3) {
            logger.error(EXCEPTION_NOT_BE_CLOSED_MESSAGE);
            throw new ServiceException(EXCEPTION_NOT_BE_CLOSED_MESSAGE);
        } else {
            if (orderStatus == (byte) Status.NEW.getId() || orderStatus == (byte) Status.READY.getId()) {
                adminDto.setBalance(adminDto.getBalance() - amount);
                accountDto.setBalance(accountDto.getBalance() + amount);
            }
            order.setStatus(Status.CLOSE.getId());
            order.setCurrentSum(AMOUNT_IS_ZERO);
            order.setPaymentStatus((byte) 1);
            Car car = carConverter.convert(carDto);
            car.setAvailable((byte) 1);
            Account account = accountConverter.convert(accountDto);
            Account admin = accountConverter.convert(adminDto);
            try {
                return accountDao.cancelOrder(account, admin, car, order);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }

    /**
     * Checks a parameter for null
     *
     * @param param string
     * @return the boolean
     */
    @Override
    public Boolean checkNotNull(String param) {
        return param.isEmpty();
    }
}

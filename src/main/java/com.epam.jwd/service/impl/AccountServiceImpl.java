package com.epam.jwd.service.impl;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.AccountDaoImpl;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.order.Order;
import com.epam.jwd.dao.model.order.Status;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.impl.AccountConverter;
import com.epam.jwd.service.converter.impl.CarConverter;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AccountServiceImpl implements Service<AccountDto, Integer> {
    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);
    private static final String EXCEPTION_NOT_BE_CLOSED_MESSAGE = "The order cannot be closed. ";
    private static final Double AMOUNT_IS_ZERO = 0.00;
    private final AccountDaoImpl accountDao = new AccountDaoImpl();
    private final AccountConverter accountConverter = new AccountConverter();
    private final CarConverter carConverter = new CarConverter();

    @Override
    public AccountDto create(AccountDto accountDto) throws ServiceException {
        return null;
    }

    @Override
    public Boolean update(AccountDto accountDto) throws DaoException {
        logger.info("update method " + AccountServiceImpl.class);
        Account account = accountConverter.convert(accountDto);
        return accountDao.update(account);
    }

    @Override
    public Boolean delete(AccountDto accountDto) throws ServiceException {
        return null;
    }

    @Override
    public AccountDto getById(Integer id) throws DaoException, ServiceException {
        logger.info("get by id method " + AccountServiceImpl.class);
        Account account = accountDao.findById(id);
        return accountConverter.convert(account);
    }

    @Override
    public List<AccountDto> getAll() throws ServiceException, DaoException {
        logger.info("get all method " + AccountServiceImpl.class);
        List<AccountDto> accountDtos = new ArrayList<>();
        for (Account account : accountDao.findAll()) {
            accountDtos.add(accountConverter.convert(account));
        }
        return accountDtos;
    }

    public Boolean transferAmountAccountAdmin(AccountDto user, Order order) throws DaoException, ServiceException {
        logger.info("transfer from user to admin method " + AccountServiceImpl.class);
        Double accountBalance = user.getBalance();
        Double amount = order.getCurrentSum();
        if (accountBalance >= amount) {
            AccountDto admin = accountConverter.convert(accountDao.findById(1));
            admin.setBalance(admin.getBalance() + amount);
            user.setBalance(user.getBalance() - amount);
            order.setStatus(Status.NEW.getId());
            order.setPaymentStatus((byte) 1);
            Account daoAdmin = accountConverter.convert(admin);
            Account daoUser = accountConverter.convert(user);
            return accountDao.saveTransactionUserAdmin(daoUser, daoAdmin, order);
        }
        return false;
    }

    public Boolean cancelOrder(AccountDto accountDto, CarDto carDto, Order order) throws ServiceException, DaoException {
        logger.info("cancel order method " + AccountServiceImpl.class);
        Byte orderStatus = order.getStatus();
        AccountDto adminDto = accountConverter.convert(accountDao.findById(1));
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
            return accountDao.cancelOrder(account, admin, car, order);
        }
    }

    public Boolean checkNotNull(String param){
        return param.isEmpty();
    }
}

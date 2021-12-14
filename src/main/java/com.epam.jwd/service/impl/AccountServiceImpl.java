package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.impl.AccountDaoImpl;
import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.impl.AccountConverter;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.exception.ValidateException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.AccountValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AccountServiceImpl implements Service<AccountDto, Integer> {
    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    private final AccountDaoImpl dao = new AccountDaoImpl();
    private final AccountConverter converter = new AccountConverter();
    private final AccountValidator validator = new AccountValidator();

    @Override
    public AccountDto create(AccountDto accountDto) throws ServiceException {
        return null;
    }

    @Override
    public Boolean update(AccountDto accountDto) throws ServiceException {
        logger.info("update method " + AccountServiceImpl.class);
        try {
            Account account = converter.convert(accountDto);
            return dao.update(account);
        }catch (Exception e){
            logger.info("update method " + AccountServiceImpl.class);
            throw new ServiceException(ValidateException.UPDATE);
        }
    }

    @Override
    public Boolean delete(AccountDto accountDto) throws ServiceException {
        return null;
    }

    @Override
    public AccountDto getById(Integer id) throws ServiceException {
        logger.info("get by id method " + AccountServiceImpl.class);
        Account account = dao.findById(id);
        return converter.convert(account);
    }

    @Override
    public List<AccountDto> getAll() throws ServiceException {
        logger.info("get all method " + AccountServiceImpl.class);
        List<AccountDto> accountDtos = new ArrayList<>();
        for (Account account : dao.findAll()) {
            accountDtos.add(converter.convert(account));
        }
        return accountDtos;
    }

    public AccountDto getByPhone(String phone) {
        logger.info("get by phone method " + AccountServiceImpl.class);
        Account account = ((AccountDaoImpl)dao).findByPhone(phone);
        return converter.convert(account);
    }

    public Boolean inputIsNull(AccountDto accountDto){
        if (accountDto.getName() == null
        || accountDto.getSurname() == null
        || accountDto.getPhone() == null
        || accountDto.getDocumentId() == null
        || accountDto.getAddress() ==null
        || accountDto.getDriveLicenseNumber() == null) {
            return false;
        } else {
            return true;
        }
    }

    public AccountDto addAmount(AccountDto accountDto, Double sum){
        Double before = accountDto.getBalance();
        accountDto.setBalance(before + sum);
        return accountDto;
    }

    public AccountDto SubtractAmount(AccountDto accountDto, Double sum){
        Double before = accountDto.getBalance();
        accountDto.setBalance(before - sum);
        return accountDto;
    }
}

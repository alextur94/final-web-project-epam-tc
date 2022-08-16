package com.epam.jwd.service.validator.api;

import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.exception.ServiceException;

public interface AccountValidator extends Validator<AccountDto, Integer> {
    void validateEmail(String email) throws ServiceException;
    void validateEmailUnique(Account account) throws ServiceException;
}

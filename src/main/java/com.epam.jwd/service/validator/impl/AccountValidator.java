package com.epam.jwd.service.validator.impl;

import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.exception.ValidateException;
import com.epam.jwd.service.validator.api.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class AccountValidator implements Validator<AccountDto, Integer> {
    private static final Logger logger = LogManager.getLogger(AccountValidator.class);

    /**
     * Checking that the user is not empty
     *
     * @param accountDto
     */
    @Override
    public void validate(AccountDto accountDto) throws ServiceException {
        if (Objects.isNull(accountDto)) {
            logger.info(ValidateException.ACCOUNT_IS_EMPTY + AccountValidator.class);
            throw new ServiceException(ValidateException.ACCOUNT_IS_EMPTY);
        }
        validateEmail(accountDto.getEmail());
    }

    /**
     * Checking that mail is not missing
     *
     * @param email
     */
    public void validateEmail(String email) throws ServiceException {
        if (Objects.isNull(email)) {
            logger.info(ValidateException.EMAIL + AccountValidator.class);
            throw new ServiceException(ValidateException.EMAIL);
        }
    }

    /**
     * Check for uniqueness of email
     *
     * @param account
     */
    public void validateEmailUnique(Account account) throws ServiceException {
        if (!Objects.isNull(account)) {
            throw new ServiceException(ValidateException.EMAIL_IS_NOT_UNIQUE);
        }
    }
}

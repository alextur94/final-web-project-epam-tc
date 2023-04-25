package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.exception.ServiceException;

public class AccountConverterImpl implements Converter<Account, AccountDto, Integer> {
    @Override
    public Account convert(AccountDto accountDto) {
        return new Account.Builder()
                .withId(accountDto.getId())
                .withRole(accountDto.getRole())
                .withName(accountDto.getName())
                .withSurname(accountDto.getSurname())
                .withEmail(accountDto.getEmail())
                .withPhone(accountDto.getPhone())
                .withDocumentId(accountDto.getDocumentId())
                .withAddress(accountDto.getAddress())
                .withDriveLicenseNumber(accountDto.getDriveLicenseNumber())
                .withBalance(accountDto.getBalance())
                .withStatus(accountDto.getStatus())
                .build();
    }

    @Override
    public AccountDto convert(Account account) throws ServiceException {
        return new AccountDto.Builder()
                .withId(account.getId())
                .withRole(account.getRole())
                .withName(account.getName())
                .withSurname(account.getSurname())
                .withEmail(account.getEmail())
                .withPhone(account.getPhone())
                .withDocumentId(account.getDocumentId())
                .withAddress(account.getAddress())
                .withDriveLicenseNumber(account.getDriveLicenseNumber())
                .withBalance(account.getBalance())
                .withStatus(account.getStatus())
                .build();
    }
}

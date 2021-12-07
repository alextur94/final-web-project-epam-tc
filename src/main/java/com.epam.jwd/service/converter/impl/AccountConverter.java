package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.account.Account;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.AccountDto;

public class AccountConverter implements Converter<Account, AccountDto, Integer> {
    @Override
    public Account convert(AccountDto accountDto) {
        return new Account.Builder()
                .withId(accountDto.getId())
                .withRoleId(accountDto.getRoleId())
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
    public AccountDto convert(Account account) {
        return new AccountDto.Builder()
                .withId(account.getId())
                .withRoleId(account.getRoleId())
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

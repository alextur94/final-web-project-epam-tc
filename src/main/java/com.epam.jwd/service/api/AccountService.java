package com.epam.jwd.service.api;

import com.epam.jwd.dao.model.order.Order;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.exception.ServiceException;

public interface AccountService extends Service<AccountDto, Integer>  {
    AccountDto getByEmailForUpdate(String email) throws ServiceException;
    Boolean transferAmountAccountAdmin(AccountDto user, Order order) throws ServiceException;
    Boolean cancelOrder(AccountDto accountDto, CarDto carDto, Order order) throws ServiceException;
    Boolean checkNotNull(String param);
}

package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.dto.OrderDto;
import com.epam.jwd.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface OrderService extends Service<OrderDto, Integer> {
    Boolean cancelOrderAdmin(Integer orderId, String refusal) throws ServiceException;
    Boolean createOrder(CarDto carDto, Integer userId, Integer day, Byte type) throws ServiceException;
    Boolean beginRent(Integer orderId) throws ServiceException;
    Boolean endRent(Integer orderId, Double amountDamage, String markDesc) throws ServiceException;
    List<OrderDto> getByStatus(Integer status) throws ServiceException;
    List<OrderDto> findCountOrders(Integer start, Integer count, Integer userId) throws ServiceException;
    Integer getCountRowByStatus(Integer status) throws ServiceException;
    void approveOrder(Integer orderId) throws ServiceException;
    void onlyOne(Integer userId) throws ServiceException;
    Map<Integer, AccountDto> unionUserAndAccount(Integer status) throws ServiceException;
}

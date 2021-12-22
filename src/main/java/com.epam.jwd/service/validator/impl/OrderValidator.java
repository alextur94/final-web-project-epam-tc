package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.OrderDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.exception.ValidateException;
import com.epam.jwd.service.validator.api.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class OrderValidator implements Validator<OrderDto, Integer> {
    private static final Logger logger = LogManager.getLogger(OrderValidator.class);
    private static final Double MIN_NUMBER = 0.00;

    /**
     * Checking that there is an order
     *
     * @param orderDto
     */
    @Override
    public void validate(OrderDto orderDto) throws ServiceException {
        if (Objects.isNull(orderDto)) {
            logger.info(ValidateException.PRICE_IS_EMPTY + OrderValidator.class);
            throw new ServiceException(ValidateException.PRICE_IS_EMPTY);
        }
        checkNullOrNegative(orderDto.getEndLevel());
        checkNegative(orderDto.getPledge());
    }


    /**
     * Checking that the deposit is not negative
     *
     * @param pledge
     * @return the boolean
     */
    private Boolean checkNegative(Double pledge) throws ServiceException {
        if (pledge < MIN_NUMBER) {
            throw new ServiceException(ValidateException.ORDER + OrderValidator.class);
        }
        return true;
    }

    /**
     * Checking that the deposit is not negative
     *
     * @param endLevel
     * @return the boolean
     */
    private Boolean checkNullOrNegative(Integer endLevel) throws ServiceException {
        if (endLevel == null || endLevel < 0) {
            throw new ServiceException(ValidateException.ORDER + OrderValidator.class);
        }
        return true;
    }


}

package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.PriceDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.exception.ValidateException;
import com.epam.jwd.service.validator.api.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class PriceValidator implements Validator<PriceDto, Integer> {
    private static final Logger logger = LogManager.getLogger(PriceValidator.class);
    private static final Double PRICE_MIN = 0.00;

    @Override
    public void validate(PriceDto priceDto) throws ServiceException {
        if (Objects.isNull(priceDto)) {
            logger.info(ValidateException.PRICE_IS_EMPTY + PriceValidator.class);
            throw new ServiceException(ValidateException.PRICE_IS_EMPTY);
        }
        validatePriceDay(priceDto.getPricePerDay());
    }

    private void validatePriceDay(Double pricePerDay) throws ServiceException {
        priceNotBeNegative(pricePerDay);
    }

    private void priceNotBeNegative(Double price) throws ServiceException {
        if (price < PRICE_MIN) {
            logger.info(ValidateException.PRICE);
            throw new ServiceException(ValidateException.PRICE);
        }
    }
}

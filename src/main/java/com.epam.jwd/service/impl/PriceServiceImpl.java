package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.impl.PriceDaoImpl;
import com.epam.jwd.dao.model.price.Price;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.impl.PriceConverter;
import com.epam.jwd.service.dto.PriceDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.exception.ValidateException;
import com.epam.jwd.service.validator.impl.PriceValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class PriceServiceImpl implements Service<PriceDto, Integer> {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final Dao<Price, Integer> dao = new PriceDaoImpl();
    private final PriceConverter converter = new PriceConverter();
    private final PriceValidator validator = new PriceValidator();

    @Override
    public PriceDto save(PriceDto value) throws ServiceException, SQLException {
        logger.info("create method " + PriceServiceImpl.class);
        validator.validate(value);
        value.setPricePerHour(Math.ceil(value.getPricePerDay()/6));
        Price price = converter.convert(value);
        return converter.convert(dao.save(price));
    }

    @Override
    public Boolean update(PriceDto priceDto) throws ServiceException {
        logger.info("update method " + PriceServiceImpl.class);
        try {
            validator.validate(priceDto);
            Price price = converter.convert(priceDto);
            dao.update(price);
            return true;
        } catch (Exception e) {
            throw new ServiceException(ValidateException.PRICE);
        }
    }

    @Override
    public Boolean delete(PriceDto priceDto) throws ServiceException {
        logger.info("delete method " + PriceServiceImpl.class);
        try {
            dao.delete(converter.convert(priceDto));
            return true;
        } catch (Exception e) {
            throw new ServiceException(ValidateException.DELETE);
        }
    }

    @Override
    public PriceDto getById(Integer id) throws ServiceException {
        logger.info("get by id method " + PriceServiceImpl.class);
        try {
            return converter.convert(dao.findById(id));
        } catch (Exception e) {
            throw new ServiceException(ValidateException.DELETE);
        }
    }

    @Override
    public List<PriceDto> getAll() {
        return null;
    }
}

package com.epam.jwd.service.impl;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.PriceDaoImpl;
import com.epam.jwd.dao.model.price.Price;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.impl.PriceConverter;
import com.epam.jwd.service.dto.PriceDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.impl.PriceValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PriceServiceImpl implements Service<PriceDto, Integer> {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    public final Integer NUMBER_HOUR = 6;
    private final PriceDaoImpl priceDao = new PriceDaoImpl();
    private final PriceConverter converter = new PriceConverter();
    private final PriceValidator validator = new PriceValidator();

    @Override
    public PriceDto create(PriceDto value) throws ServiceException, DaoException {
        logger.info("create method " + PriceServiceImpl.class);
        validator.validate(value);
        value.setPricePerHour(Math.ceil(value.getPricePerDay() / NUMBER_HOUR));
        Price price = converter.convert(value);
        return converter.convert(priceDao.save(price));
    }

    @Override
    public Boolean update(PriceDto priceDto) throws ServiceException, DaoException {
        logger.info("update method " + PriceServiceImpl.class);
        validator.validate(priceDto);
        Price price = converter.convert(priceDto);
        priceDao.update(price);
        return true;
    }

    @Override
    public Boolean delete(PriceDto priceDto) throws ServiceException, DaoException {
        logger.info("delete method " + PriceServiceImpl.class);
        priceDao.delete(converter.convert(priceDto));
        return true;
    }

    @Override
    public PriceDto getById(Integer id) throws ServiceException, DaoException {
        logger.info("get by id method " + PriceServiceImpl.class);
        return converter.convert(priceDao.findById(id));
    }

    @Override
    public List<PriceDto> getAll() throws DaoException, ServiceException {
        logger.info("get all method " + PriceServiceImpl.class);
        List<PriceDto> priceDto = new ArrayList<>();
        for (Price price : priceDao.findAll()) {
            priceDto.add(converter.convert(price));
        }
        return priceDto;
    }
}

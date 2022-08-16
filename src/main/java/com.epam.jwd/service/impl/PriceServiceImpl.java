package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.PriceDao;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.PriceDaoImpl;
import com.epam.jwd.dao.model.price.Price;
import com.epam.jwd.service.api.PriceService;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.PriceConverterImpl;
import com.epam.jwd.service.dto.PriceDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.PriceValidator;
import com.epam.jwd.service.validator.impl.PriceValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PriceServiceImpl implements PriceService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private PriceDao priceDao = new PriceDaoImpl();
    private PriceValidator validator = new PriceValidatorImpl();
    private Converter<Price, PriceDto, Integer> converter = new PriceConverterImpl();
    public final Integer NUMBER_HOUR = 6;

    /**
     * Validate, convert and create new entity
     *
     * @param value
     * @return CarDto
     */
    @Override
    public PriceDto create(PriceDto value) throws ServiceException {
        logger.info("create method " + PriceServiceImpl.class);
        validator.validate(value);
        value.setPricePerHour(Math.ceil(value.getPricePerDay() / NUMBER_HOUR));
        Price price = converter.convert(value);
        Price priceReturn = null;
        try {
            priceReturn = priceDao.save(price);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return converter.convert(priceReturn);
    }

    /**
     * Convert and submitting an entity to save
     *
     * @param priceDto
     * @return the boolean
     */
    @Override
    public Boolean update(PriceDto priceDto) throws ServiceException {
        logger.info("update method " + PriceServiceImpl.class);
        validator.validate(priceDto);
        Price price = converter.convert(priceDto);
        try {
            priceDao.update(price);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public Boolean delete(PriceDto priceDto) throws ServiceException {
        return null;
    }

    /**
     * Get by id PriceDto
     *
     * @param id
     * @return PriceDto
     */
    @Override
    public PriceDto getById(Integer id) throws ServiceException {
        logger.info("get by id method " + PriceServiceImpl.class);
        try {
            return converter.convert(priceDao.findById(id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Get all records
     *
     * @return List<PriceDto></>
     */
    @Override
    public List<PriceDto> getAll() throws ServiceException {
        logger.info("get all method " + PriceServiceImpl.class);
        List<PriceDto> priceDto = new ArrayList<>();
        try {
            for (Price price : priceDao.findAll()) {
                priceDto.add(converter.convert(price));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return priceDto;
    }
}

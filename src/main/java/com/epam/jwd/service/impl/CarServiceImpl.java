package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.CarDao;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.CarDaoImpl;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.price.Price;
import com.epam.jwd.service.api.CarService;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.CarConverterImpl;
import com.epam.jwd.service.converter.impl.PriceConverterImpl;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.dto.PriceDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.PriceValidator;
import com.epam.jwd.service.validator.impl.PriceValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CarServiceImpl implements CarService {
    private static final Logger logger = LogManager.getLogger(CarServiceImpl.class);
    private CarDao carDao = new CarDaoImpl();
    private PriceValidator validatorPrice = new PriceValidatorImpl();
    private Converter<Car, CarDto, Integer> converterCar = new CarConverterImpl();
    private Converter<Price, PriceDto, Integer> converterPrice = new PriceConverterImpl();

    /**
     * Convert and create new entity
     *
     * @param carDto entity
     * @return CarDto
     */
    @Override
    public CarDto create(CarDto carDto) throws ServiceException {
        logger.info("create method " + CarServiceImpl.class);
        Car car = converterCar.convert(carDto);
        try {
            return converterCar.convert(carDao.save(car));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Boolean update(CarDto value) throws ServiceException {
        return null;
    }

    @Override
    public Boolean delete(CarDto value) throws ServiceException {
        return null;
    }

    /**
     * Convert and get entity by id
     *
     * @param id emtity
     * @return CarDto entity
     */
    @Override
    public CarDto getById(Integer id) throws ServiceException {
        logger.info("get by id method " + CarServiceImpl.class);
        try {
            return converterCar.convert(carDao.findById(id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Retrieving all records
     *
     * @return List CarDTO
     */
    @Override
    public List<CarDto> getAll() throws ServiceException {
        logger.info("get all method " + CarServiceImpl.class);
        List<CarDto> carDto = new ArrayList<>();
        try {
            for (Car car : carDao.findAll()) {
                carDto.add(converterCar.convert(car));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return carDto;
    }

    /**
     * Count row from table cars
     *
     * @return the integer
     */
    @Override
    public Integer getCountRowFromCars() throws ServiceException {
        logger.info("get all by range method " + CarServiceImpl.class);
        try {
            return carDao.countRowFromCars();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Validate entity. Get fields priceInDay in table Price
     *
     * @param carDto and priceDto
     * @return the boolean
     */
    @Override
    public Boolean saveCar(CarDto carDto, PriceDto priceDto) throws ServiceException {
        logger.info("save car method " + CarServiceImpl.class);
        validatorPrice.validate(priceDto);
        Car car = converterCar.convert(carDto);
        Price price = converterPrice.convert(priceDto);
        price.setPricePerHour(Math.ceil(price.getPricePerDay() / 6));
        try {
            return carDao.saveCarPrice(car, price);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}

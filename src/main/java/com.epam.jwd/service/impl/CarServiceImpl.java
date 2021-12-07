package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.impl.CarDaoImpl;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.price.Price;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.CarConverter;
import com.epam.jwd.service.converter.impl.PriceConverter;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.dto.PriceDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.CarValidator;
import com.epam.jwd.service.validator.impl.PriceValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class CarServiceImpl implements Service<CarDto, Integer> {
    private static final Logger logger = LogManager.getLogger(CarServiceImpl.class);

    private final Dao<Car, Integer> dao = new CarDaoImpl();
    private final Converter<Car, CarDto, Integer> converterCar = new CarConverter();
    private final Converter<Price, PriceDto, Integer> converterPrice = new PriceConverter();
    private final Validator<CarDto, Integer> validatorCar = new CarValidator();
    private final Validator<PriceDto, Integer> validatorPrice = new PriceValidator();


    @Override
    public CarDto save(CarDto carDto) throws ServiceException, SQLException {
        logger.info("create method " + CarServiceImpl.class);
        validatorCar.validate(carDto);
        Car car = converterCar.convert(carDto);
        return converterCar.convert(dao.save(car));
    }

    @Override
    public Boolean update(CarDto value) throws ServiceException {
        return null;
    }

    @Override
    public Boolean delete(CarDto value) throws ServiceException {
        return null;
    }

    @Override
    public CarDto getById(Integer id) throws ServiceException {
        return null;
    }

    @Override
    public List<CarDto> getAll() throws ServiceException {
        return null;
    }

    public void saveCar(CarDto carDto, PriceDto priceDto) throws ServiceException {
        logger.info("save car method " + CarServiceImpl.class);
        validatorCar.validate(carDto);
        validatorPrice.validate(priceDto);
        Car car = converterCar.convert(carDto);
        Price price = converterPrice.convert(priceDto);
        price.setPricePerHour(Math.ceil(price.getPricePerDay()/6));
        ((CarDaoImpl)dao).saveCarPrice(car, price);
    }
}

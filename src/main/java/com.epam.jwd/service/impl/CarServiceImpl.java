package com.epam.jwd.service.impl;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.CarDaoImpl;
import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.dao.model.price.Price;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.impl.CarConverter;
import com.epam.jwd.service.converter.impl.PriceConverter;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.dto.PriceDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.impl.CarValidator;
import com.epam.jwd.service.validator.impl.PriceValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CarServiceImpl implements Service<CarDto, Integer> {
    private static final Logger logger = LogManager.getLogger(CarServiceImpl.class);
    private final CarDaoImpl carDao = new CarDaoImpl();
    private final CarConverter converterCar = new CarConverter();
    private final PriceConverter converterPrice = new PriceConverter();
    private final CarValidator validatorCar = new CarValidator();
    private final PriceValidator validatorPrice = new PriceValidator();

    @Override
    public CarDto create(CarDto carDto) throws DaoException, ServiceException {
        logger.info("create method " + CarServiceImpl.class);
        validatorCar.validate(carDto);
        Car car = converterCar.convert(carDto);
        return converterCar.convert(carDao.save(car));
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
    public CarDto getById(Integer id) throws DaoException, ServiceException {
        logger.info("get by id method " + CarServiceImpl.class);
        return converterCar.convert(carDao.findById(id));
    }

    @Override
    public List<CarDto> getAll() throws ServiceException, DaoException {
        logger.info("get all method " + CarServiceImpl.class);
        List<CarDto> carDto = new ArrayList<>();
        for (Car car : carDao.findAll()) {
            carDto.add(converterCar.convert(car));
        }
        return carDto;
    }

    public Boolean saveCar(CarDto carDto, PriceDto priceDto) throws ServiceException, DaoException {
        logger.info("save car method " + CarServiceImpl.class);
        validatorCar.validate(carDto);
        validatorPrice.validate(priceDto);
        Car car = converterCar.convert(carDto);
        Price price = converterPrice.convert(priceDto);
        price.setPricePerHour(Math.ceil(price.getPricePerDay() / 6));
        return carDao.saveCarPrice(car, price);
    }
}

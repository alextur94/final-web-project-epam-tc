package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.car.Car;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.exception.ServiceException;

public class CarConverter implements Converter<Car, CarDto, Integer> {
    @Override
    public Car convert(CarDto carDto) {
        return new Car.Builder()
                .withId(carDto.getId())
                .withBrand(carDto.getBrand())
                .withModel(carDto.getModel())
                .withYear(carDto.getYear())
                .withLevel(carDto.getLevel())
                .withBody(carDto.getBody())
                .withEngineVolume(carDto.getEngineVolume())
                .withTransmission(carDto.getTransmission())
                .withDoors(carDto.getDoors())
                .withColor(carDto.getColor())
                .withAvailable(carDto.getAvailable())
                .withPriceId(carDto.getPriceId())
                .build();
    }

    @Override
    public CarDto convert(Car car) throws ServiceException {
        return new CarDto.Builder()
                .withId(car.getId())
                .withBrand(car.getBrand())
                .withModel(car.getModel())
                .withYear(car.getYear())
                .withLevel(car.getLevel())
                .withBody(car.getBody())
                .withEngineVolume(car.getEngineVolume())
                .withTransmission(car.getTransmission())
                .withDoors(car.getDoors())
                .withColor(car.getColor())
                .withAvailable(car.getAvailable())
                .withPriceId(car.getPriceId())
                .build();
    }
}

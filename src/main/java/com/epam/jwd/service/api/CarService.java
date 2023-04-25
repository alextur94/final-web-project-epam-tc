package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.dto.PriceDto;
import com.epam.jwd.service.exception.ServiceException;

public interface CarService extends Service<CarDto, Integer>{
    Integer getCountRowFromCars() throws ServiceException;
    Boolean saveCar(CarDto carDto, PriceDto priceDto) throws ServiceException;
}

package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

public class CarValidator implements Validator<CarDto, Integer> {
    @Override
    public void validate(CarDto value) throws ServiceException {

    }
}

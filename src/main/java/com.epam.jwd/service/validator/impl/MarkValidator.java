package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.MarkDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

public class MarkValidator implements Validator<MarkDto, Integer> {
    @Override
    public void validate(MarkDto value) throws ServiceException {

    }
}

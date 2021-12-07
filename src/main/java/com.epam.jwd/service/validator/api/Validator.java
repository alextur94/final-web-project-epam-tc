package com.epam.jwd.service.validator.api;

import com.epam.jwd.service.dto.AbstractDto;
import com.epam.jwd.service.exception.ServiceException;

public interface Validator<T extends AbstractDto<K>, K> {
    void validate(T value) throws ServiceException;
}

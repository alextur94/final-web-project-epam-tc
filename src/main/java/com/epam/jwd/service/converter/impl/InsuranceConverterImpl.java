package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.insurance.Insurance;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.InsuranceDto;
import com.epam.jwd.service.exception.ServiceException;

public class InsuranceConverterImpl implements Converter<Insurance, InsuranceDto, Integer> {
    @Override
    public Insurance convert(InsuranceDto insuranceDto) {
        return new Insurance(
                insuranceDto.getId(),
                insuranceDto.getType(),
                insuranceDto.getNumber(),
                insuranceDto.getCompany(),
                insuranceDto.getAmount()
        );
    }

    @Override
    public InsuranceDto convert(Insurance insurance) throws ServiceException {
        return new InsuranceDto(
                insurance.getId(),
                insurance.getType(),
                insurance.getNumber(),
                insurance.getCompany(),
                insurance.getAmount()
        );
    }
}

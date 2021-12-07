package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.price.Price;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.PriceDto;

public class PriceConverter implements Converter<Price, PriceDto, Integer> {
    @Override
    public Price convert(PriceDto priceDto) {
        return new Price(
                priceDto.getId(),
                priceDto.getPricePerDay(),
                priceDto.getPricePerHour()
        );
    }

    @Override
    public PriceDto convert(Price price) {
        return new PriceDto(
                price.getId(),
                price.getPricePerDay(),
                price.getPricePerHour()
        );
    }
}

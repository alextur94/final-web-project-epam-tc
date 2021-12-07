package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.mark.Mark;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.MarkDto;

public class MarkConverter implements Converter<Mark, MarkDto, Integer> {
    @Override
    public Mark convert(MarkDto markDto) {
        return new Mark(
                markDto.getId(),
                markDto.getDescription());
    }

    @Override
    public MarkDto convert(Mark mark) {
        return new MarkDto(
                mark.getId(),
                mark.getDescription());
    }
}
